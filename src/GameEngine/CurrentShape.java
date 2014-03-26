
package GameEngine;

public class CurrentShape extends Shape {
  private int x, y;
  
  public CurrentShape(String name, int[] color, int[][] representation) {
    super(name, color, representation);
    x = 0; y = 0;
  }
  /*
  private void setXY(int x, int y){ this.x = x; this.y = y; }
  
  private void setX(int x){ this.x = x; }
  
  private void setY(int y){ this.y = y; }
  */
  
  public int getX(){ return x; }
  
  public int getY(){ return y; }
  
  public boolean moveDown(){
    return false;
  }
  
  public void rotateLeft(){
    
  }
  
  public void rotateRight(){
    
  }
}
