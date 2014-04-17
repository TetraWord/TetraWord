package GameEngine;

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

  public BoardGame(int nb, Shape s, Shape s2, Player p) {
    /* Init attributes */
    this.myPlayer = p;
    this.grid = new Grid(this, new CurrentShape(s));
    this.hub = new Hub();
    this.listNextShape.add(s2);

    this.myPlayer.addObservateur(hub);
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
        System.out.println("j'ai tapé : " + myPlayer.getWord());
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

  @Override
  public void update(Observable o, Object args) {
    if (args instanceof int[] && allowClick) {
      /*We select the clicked Brick */
      int x = ((int[]) args)[0];
      int y = ((int[]) args)[1];
      Brick b = grid.getTGrid()[y][x];
      /*If the brick is on the full line and if it's not clicked yet */
      if (y == grid.getFirstFullLine() && !b.isClicked()) {
        myPlayer.addNewChar(b.getLetter());
        b.setClicked(true);
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

}
