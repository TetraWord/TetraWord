package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BoardGame implements Observable {

  private final Grid grid;
  private final static ShapesStock ss = ShapesStock.getInstance();
  private final int nb;
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final Player myPlayer;
  private final Queue<Shape> listNextShape = new LinkedList<>();

  public BoardGame(int nb, Shape s, Shape s2, Player p) {
    this.nb = nb;
    this.myPlayer = p;

    listNextShape.add(s);
    listNextShape.add(s2);

    grid = new Grid(this, new CurrentShape(s));
  }

  public void setInGrid(CurrentShape s) {
    grid.setIn(s);
  }

  public Grid getGrid() {
    return grid;
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

  public CurrentShape launchNextShape() {
    int size = listNextShape.size();
    CurrentShape cs = new CurrentShape(listNextShape.poll());
    Shape s = new CurrentShape(getRandomShape());

    Player[] tabP = GameEngine.getInstance().getPlayers();
    for (int i = 0; i < GameEngine.getInstance().getNbPlayers(); ++i) {
      tabP[i].getBoardGame().listNextShape.add(s);
    }
    
    updateObservateur();
    
    return cs;
  }

  public Shape getNextShape(){
    return listNextShape.element();
  }
  
  @Override
  public void addObservateur(Observer obs) {
    listObserver.add(obs);
  }

  @Override
  public void updateObservateur() {
    for (Observer obs : listObserver) {
      obs.update(this, null);
    }
  }

  @Override
  public void delObservateur() {
    listObserver = new ArrayList<>();
  }

}
