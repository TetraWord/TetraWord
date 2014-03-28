package GameEngine;

public class Grid {
  private final int[][] tGrid = new int[10][20];
  
  public Grid(){
    for( int i = 0; i < 10; ++i ){
      for( int j = 0; j < 20; ++j ){
        tGrid[i][j] = -1;
      }
    }
  }
}
