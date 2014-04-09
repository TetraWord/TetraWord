package GameEngine;

import java.awt.Color;

public class Shape {

  public enum ShapeEnum {

    NoShape, LShape, MirroredLShape, SShape, ZShape, LineShape, SquareShape, TShape
  };

  protected final String name;
  protected final Color color;
  protected int[][] representation;

  public Shape(String name, int[] color, int[][] representation) {
    this.name = name;
    this.color = new Color(color[0], color[1], color[2]);
    this.representation = representation;
  }

  public Shape(String name, Color color, int[][] representation) {
    this.name = name;
    this.color = color;
    this.representation = representation;
  }

  public Color getRGB() {
    return color;
  }

  public int getRep(int x, int y) {
    return representation[x][y];
  }

  //Copy constructor
  public Shape(Shape shape) {
    this(shape.name, shape.color, shape.representation);
  }

  public String getName() {
    return this.name;
  }

  public int[][] getRepresentation() {
    return representation;
  }
}
