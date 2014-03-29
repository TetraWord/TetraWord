package GameEngine;

public class Grid {
  private final static ShapesStock ss = ShapesStock.getInstance();
  private final int[][] tGrid = new int[10][20];
  private CurrentShape currentShape;
  
  public Grid(){
    Shape s = ss.getRandomShape();
    currentShape = new CurrentShape(s);
    
    for( int i = 0; i < 10; ++i ){
      for( int j = 0; j < 20; ++j ){
        tGrid[i][j] = -1;
      }
    }
  }
  
  public CurrentShape getCurrentShape(){
    return currentShape;
  }
  
  public void launchNextShape(Shape s){
    currentShape = new CurrentShape(s);
  }
}
