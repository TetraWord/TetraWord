package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.awt.Color;
import java.util.ArrayList;

public class Shape implements Observable {

  public enum ShapeEnum {

    NoShape, LShape, MirroredLShape, SShape, ZShape, LineShape, SquareShape, TShape
  };

  public final static int sizeShape = 4;
  protected final String name;
  protected final Color color;
  protected int[][] representation;
  protected Brick[][] composition;
  private ArrayList<Observer> listObserver = new ArrayList<>();

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

  public Shape(String name, Color color, int[][] representation, Brick[][] composition) {
    this.name = name;
    this.color = color;
    this.representation = representation;
    this.composition = composition;
  }

  public Shape(String name, int[] color, int[][] representation) {
    this.name = name;
    this.color = new Color(color[0], color[1], color[2]);
    this.representation = representation;
    setComposition(representation);
  }

  public Shape(String name, Color color, int[][] representation) {
    this.name = name;
    this.color = color;
    this.representation = representation;
    setComposition(representation);
  }

  //Copy constructor
  public Shape(Shape shape) {
    this(shape.name, shape.color, shape.representation);
  }

  private void setComposition(int[][] representation) {
    this.composition = new Brick[representation.length][representation[0].length];
    Color c = this.getColor();
    for (int i = 0; i < representation.length; ++i) {
      for (int j = 0; j < representation[i].length; ++j) {
        if (representation[i][j] > 0) { //suivant comment est implémenter la représentation
          composition[i][j] = new Brick(1, c); //rajouter une lettre au hasard
        }
      }
    }
    updateObservateur(null);
  }

  public Brick[][] getComposition() {
    return composition;
  }

  public Color getColor() {
    return color;
  }

  public int getRep(int x, int y) {
    return representation[x][y];
  }

  public Color getRGB() {
    return color;
  }

  public String getName() {
    return this.name;
  }

  public int[][] getRepresentation() {
    return representation;
  }
}
