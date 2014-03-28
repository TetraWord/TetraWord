
package GameEngine;

public class CurrentShape extends Shape {
  private int curX, curY;
  private final Brick[][] composition;
  
  public CurrentShape(String name, int[] color, int[][] representation) {
    super(name, color, representation);
    curX = 0; curY = 0;
    
    this.composition = new Brick[representation.length][representation[0].length]; //pas sûr de ce truc
    for( int i = 0; i < representation.length; ++i ){
      for( int j = 0; i < representation[i].length; ++j ){
        if( representation[i][j] > 0 ){ //suivant comment est implémenter la représentation
          composition[i][j] = new Brick('a'); //rajouter une lettre au hasard
        }
      }
    }
    
  }
  /*
  private void setXY(int x, int y){ this.x = x; this.y = y; }
  
  private void setX(int x){ this.x = x; }
  
  private void setY(int y){ this.y = y; }
  */
  
  public int getX(){ return curX; }
  
  public int getY(){ return curY; }
 
  public void rotateLeft(){
    
  }
  
  public void rotateRight(){
    
  }
}
