package GameEngine;

public class Grid {
  private final static ShapesStock ss = ShapesStock.getInstance();
  private int[][] tGrid = new int[13][23];
  private CurrentShape currentShape;
  
  public Grid(){
    Shape s = ss.getRandomShape();
    currentShape = new CurrentShape(s);
    
    for( int i = 0; i < tGrid.length; ++i ){
      for( int j = 0; j < tGrid[i].length; ++j ){
        tGrid[i][j] = -1;
      }
    }
  }
  
  public int[][] getTGrid(){
  	return tGrid;
  }
  
  public void setTGrid(int[][] tg) {
  	for( int i = 0; i < tGrid.length; ++i ){
      for( int j = 0; j < tGrid[i].length; ++j ){
        tGrid[i][j] = tg[i][j];
      }
    }
  }
  
  public Shape getRandomShape(){
  	return ss.getRandomShape();
  }
  
  public CurrentShape getCurrentShape(){
    return currentShape;
  }
  
  public void launchNextShape(Shape s){
    currentShape = new CurrentShape(s);
  }
}
