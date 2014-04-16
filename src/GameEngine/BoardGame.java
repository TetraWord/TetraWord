package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BoardGame implements Observable, Observer {

  private final Grid grid;
  private final static ShapesStock ss = ShapesStock.getInstance();
  private final int nb;
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final Player myPlayer;
  private final Queue<Shape> listNextShape = new LinkedList<>();
  private boolean play;
	private final Hub hub = new Hub();
  private boolean allowClick = false;

  public BoardGame(int nb, Shape s, Shape s2, Player p) {
    this.nb = nb;
    this.myPlayer = p;
    this.play = true;

    listNextShape.add(s2);
		p.addObservateur(hub);

    grid = new Grid(this, new CurrentShape(s));
  }

  public void setPlay() {
    this.play = false;
  }

  public boolean getPlay() {
    return this.play;
  }

  public void setInGrid(CurrentShape s) {
    grid.setIn(s);
  }

  public Grid getGrid() {
    return grid;
  }

  public Hub getHub() {
    return hub;
  }

  public int getNb() {
    return nb;
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

  void removeFullLine() {
    int line = grid.getFirstFullLine();

    while (line != -1) {
      myPlayer.switchToAnagram(true);
      setAllowClick(true);

      while (myPlayer.isAnagram()) {
        System.out.println("Pour le moment : "+myPlayer.getWord());
        if(myPlayer.isWordFinished()){
          //System.out.println("j'ai tapé : "+myPlayer.getWord());
        }
      }
      
      myPlayer.clearWord();
      grid.removeLine(line);

      line = grid.getFirstFullLine();
      /* Remove this later it's just for some test 
       myBoardGame.getPlayer().setLevelUp();
       int level = myBoardGame.getPlayer().getLevel();
       myBoardGame.updateObservateur(level); */

    }

    myPlayer.switchToAnagram(false);
  }

  public void setAllowClick(boolean b) {
    allowClick = b;
  }

  @Override
  public void update(Observable o, Object args) {
    if (args instanceof int[] && allowClick) {
      /*We select the clicked Brick */
      int x = ((int[]) args)[0];
      int y = ((int[]) args)[1];
      Brick b = grid.getTGrid()[y][x];
      if( y == grid.getFirstFullLine() && !b.isClicked()){
        myPlayer.addNewChar(b.getLetter());
        b.setClicked(true);
      }
    }
  }

}
