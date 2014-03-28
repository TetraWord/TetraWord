
package GameEngine;


public class Shape {
  
  public enum ShapeEnum { NoShape, LShape, MirroredLShape, SShape, ZShape, LineShape, SquareShape, TShape };
  
  private final String name;
  private final int[] color;
  private final int[][] representation;
  
  public Shape(String name, int[] color, int[][] representation){
    this.name = name;
    this.color = color;
    this.representation = representation;
  }
  
  //Copy constructor
  public Shape(Shape shape) {
    this(shape.name, shape.color, shape.representation);
  }

}
