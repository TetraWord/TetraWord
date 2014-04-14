package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class BoardGame implements Observable {

  private final Grid grid;
  private final static ShapesStock ss = ShapesStock.getInstance();
  private final int nb;
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final Player myPlayer;
  private final Queue<Shape> listNextShape = new LinkedList<>();
  private boolean play;

  public BoardGame(int nb, Shape s, Shape s2, Player p) {
    this.nb = nb;
    this.myPlayer = p;
    this.play = true;

    listNextShape.add(s2);

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

    updateObservateur(null);

    return cs;
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

}
