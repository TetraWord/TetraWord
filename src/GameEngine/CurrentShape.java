
package GameEngine;

public class CurrentShape extends Shape {
  private int curX, curY;
  private final Brick[][] composition;
  
  public CurrentShape(String name, int[] color, int[][] representation) {
    super(name, color, representation);
    curX = 3; curY = 0;
    this.composition = new Brick[representation.length][representation[0].length];
    for( int i = 0; i < representation.length; ++i ){
      for( int j = 0; j < representation[i].length; ++j ){
        if( representation[i][j] > 0 ){ //suivant comment est implémenter la représentation
          composition[i][j] = new Brick('a'); //rajouter une lettre au hasard
        }
      }
    }
    
  }

  CurrentShape(Shape s) {
    this(s.name, s.color, s.representation);
  }
  
  public int getX(){ return curX; }
  
  public int getY(){ return curY; }
 
  public void rotateLeft(){
    
  }
  
  public void rotateRight(){
    
  }

  void tryMove(int newX, int newY) {
    if( newX >= 0 && newX < 10 ){
      curX = newX;
    }
    if( newY <= 20){
      curY = newY;  
    }
  }
}
