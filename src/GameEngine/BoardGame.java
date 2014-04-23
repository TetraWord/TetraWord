package GameEngine;

import static GameEngine.Grid.sizeX;
import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BoardGame implements Observable, Observer {

  private final Grid grid;
  private final Hub hub;
  private final Player myPlayer;
  private final static ShapesStock ss = ShapesStock.getInstance();
  private final Queue<Shape> listNextShape = new LinkedList<>();
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private boolean allowClick = false;
  private boolean allowDoubleClicked = false;
  private final ArrayList<Brick> tabBrickClicked = new ArrayList<>();
  private int[] coordsLastBrickClicked = new int[2];

  public BoardGame(int nb, Shape s, Shape s2, Player p) {
    /* Init attributes */
    this.myPlayer = p;
    this.grid = new Grid(this, new CurrentShape(s));
    this.hub = new Hub();
    this.listNextShape.add(s2);

    this.myPlayer.addObservateur(hub);
		addObservateur(hub);
    updateObservateur(null);
  }

  public void finishGame() {
    this.myPlayer.finish();
  }

  public Grid getGrid() {
    return grid;
  }

  public Hub getHub() {
    return hub;
  }

  public Player getPlayer() {
    return myPlayer;
  }

  public Shape getRandomShape() {
    return ss.getRandomShape();
  }

  public void launchNextShape() {
    int size = listNextShape.size();
    CurrentShape cs = new CurrentShape(listNextShape.poll());
    Shape s = new CurrentShape(getRandomShape());

    Player[] tabP = GameEngine.getInstance().getPlayers();
    for (int i = 0; i < GameEngine.getInstance().getNbPlayers(); ++i) {
      tabP[i].getBoardGame().listNextShape.add(s);
    }

    updateObservateur(null);

    if (!grid.isComplete(cs)) {
      grid.setCurrentShape(cs);
    }
    grid.updateObservateur(cs);
    grid.updateObservateur(grid.getTGrid());

  }

  public Shape getNextShape() {
    return listNextShape.element();
  }

  @Override
  public void addObservateur(Observer obs) {
    listObserver.add(obs);
  }

  @Override
  public void updateObservateur(Object args) {
    for (Observer obs : listObserver) {
      obs.update(this, args);
    }
  }

  @Override
  public void delObservateur() {
    listObserver = new ArrayList<>();
  }

  public int removeFullLine() {
    int numLineRemoved = 0;
    int line = grid.getFirstFullLine();

    while (line != -1) {
      setAllowClick(true);

      if (myPlayer.isWordFinished()) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < sizeX; ++j) {
          sb.append(this.getGrid().getTGrid()[line][j].getLetter());
        }
        String bestWord = myPlayer.getDico().findBestAnagramm(sb);
        System.out.println("j'ai tapé : " + myPlayer.getWord());
        if(myPlayer.getDico().included(myPlayer.getWord())) {
        	System.out.println("Ce mot existe dans le dictionnaire");
        	if ( myPlayer.getWord().equals(bestWord) || myPlayer.getWord().length() > bestWord.length()){
            myPlayer.addToScore(1000);
        		System.out.println("Le meilleur mot a ete trouve");
        	} else {
            myPlayer.addToScore(myPlayer.getWord().length() * 50);
          }
        }else{
          myPlayer.addToScore(- (myPlayer.getScore()%1000));
        }
        myPlayer.clearWord();
        grid.removeLine(line);
        ++numLineRemoved;
      }

      line = grid.getFirstFullLine();
    }

    myPlayer.switchToAnagram(false);
    return numLineRemoved;
  }

  public void setAllowClick(boolean b) {
    allowClick = b;
  }

  void setAllowDoubleClick(boolean b) {
    allowDoubleClicked = b;
  }

  @Override
  public void update(Observable o, Object args) {
    if (args instanceof int[] && allowClick) {
      /*We select the clicked Brick */
      int x = ((int[]) args)[0];
      int y = ((int[]) args)[1];
      int lastX = x;
      int lastY = y;

      if (coordsLastBrickClicked != null) {
        lastX = coordsLastBrickClicked[0];
        lastY = coordsLastBrickClicked[1];
      }

      Brick b = grid.getTGrid()[y][x];

      /*If the brick is on the full line and if it's not clicked yet */
      if (myPlayer.isAnagram()) {
        if (y == grid.getFirstFullLine() && !b.isClicked()) {
          myPlayer.addNewChar(b.getLetter());
          b.setClicked(true);
        }
      } else if (myPlayer.isWorddle()) {
        if (!b.isClicked() && !allowDoubleClicked && Math.abs(lastX - x) < 2 && Math.abs(lastY - y) < 2) {
          b.setClicked(true);
          tabBrickClicked.add(b);
          myPlayer.addNewChar(b.getLetter());
          coordsLastBrickClicked = grid.getXY(b);
        } else if (allowDoubleClicked && !b.isDoubleClicked() && b.isClicked() && Math.abs(lastX - x) < 2 && Math.abs(lastY - y) < 2) {
          doubleClickedAllBrickClicked(b);
          myPlayer.addNewChar(b.getLetter());
          coordsLastBrickClicked = grid.getXY(b);
        }
      }
    }
  }

  public void finishFall(CurrentShape s) {
    grid.setIn(s);
    if (grid.getFirstFullLine() != -1) {
      grid.setCurrentShape(null);
      myPlayer.switchToAnagram(true);
    } else {
      launchNextShape();
    }
  }

  public char clickedOneBrick() {
    Brick b = grid.clickedOneBrick();
    tabBrickClicked.add(b);
    coordsLastBrickClicked = grid.getXY(b);
    return b.getLetter();
  }

  public void declickedAllBrick() {
    grid.declickedAllBrick();
  }

  private void doubleClickedAllBrickClicked(Brick b) {
    grid.doubleClickedAllBrickClicked(b);
    setAllowDoubleClick(false);
  }

  public void setNoLastBrickClicked() {
    coordsLastBrickClicked = null;
  }

  public void setBricksToDestroy() {
    for (int i = 0; i < tabBrickClicked.size(); ++i) {
      tabBrickClicked.get(i).setInWord();
    }
    clearTabBrickClicked();
  }
  
  public void clearTabBrickClicked() {
    tabBrickClicked.clear();
  }

}
