
package GameEngine;


public class Shape {
  
  public enum ShapeEnum { NoShape, LShape, MirroredLShape, SShape, ZShape, LineShape, SquareShape, TShape };
  
  protected final String name;
  protected final int[] color;
  protected final int[][] representation;
  
  public Shape(String name, int[] color, int[][] representation){
    this.name = name;
    this.color = color;
    this.representation = representation;
  }
  
  public int getR(){ return color[0]; }
  public int getG(){ return color[1]; }
  public int getB(){ return color[2]; }
  
  //Copy constructor
  public Shape(Shape shape) {
    this(shape.name, shape.color, shape.representation);
  }
  
  public String getName() {
  	return this.name;
  }

}
