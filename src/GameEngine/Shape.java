
package GameEngine;


public class Shape {
  private final String name;
  private final int[] color;
  private final int[][] representation;
  
  public Shape(String name, int[] color, int[][] representation){
    this.name = name;
    this.color = color;
    this.representation = representation;
  }

}
