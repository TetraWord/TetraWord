
package GameEngine;

public class CurrentShape extends Shape {
  private int curX, curY;
  private final Brick[][] composition;
  
  public CurrentShape(String name, int[] color, int[][] representation) {
    super(name, color, representation);
    curX = 3; curY = -1;
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
    int[][] repTmp = new int[4][4];
    for(int i = 0; i < 4; ++i){
      for(int j = 0; j < 4; ++j){
        repTmp[i][j] = representation[3-j][i];
      }
    }
    
    representation = replaceToTopLeftCorner(repTmp);
    
    while(curX >( 10 - getMaxX(representation)) ){
      --curX;
    }
    while(curY >=( 20 - getMaxY(representation)) ){
      --curY;
    }
  }
  
  public int getMaxX(int[][] matrix){
    boolean colIsEmpty = true;
    int n = matrix.length;
    
    for(int i = (n - 1); i >= 0; --i){
      for(int j = 0; j < n; ++j ){
        if(matrix[j][i] == 1){
          colIsEmpty = false;
        }
      }
      if( !colIsEmpty ){
        return i;
      }
    }
    return 0;
  }
  
  public int getMaxY(int[][] matrix){
    boolean rowIsEmpty = true;
    int n = matrix.length;
    
    for(int i = (n - 1); i >= 0; --i){
      for(int j = 0; j < n; ++j ){
        if(matrix[i][j] == 1){
          rowIsEmpty = false;
        }
      }
      if( !rowIsEmpty ){
        return i;
      }
    }
    return 0;
  }
  
  private int[][] replaceToTopLeftCorner(int[][] matrix){
    boolean lineIsEmpty = true;
    boolean colIsEmpty = true;
    int firstLineNoEmpty = -1;
    int firstColNoEmpty = -1;
    
    for(int i = 0; i < matrix.length; ++i){
      for(int j = 0; j < matrix[i].length; ++j){
        if( matrix[i][j] == 1 ){
          lineIsEmpty = false;
        }
        if( matrix[j][i] == 1){
          colIsEmpty = false;
        }
      }
      if( !lineIsEmpty && firstLineNoEmpty == -1){
        firstLineNoEmpty = i;
      }else if( !colIsEmpty && firstColNoEmpty == -1){
        firstColNoEmpty = i;
      }
    }
    
    int prevValue = firstColNoEmpty;
    int [][] tmp = new int[matrix.length][matrix[0].length];
    for(int i = 0; i < matrix.length; ++i){
      if(firstLineNoEmpty == matrix.length){
        firstLineNoEmpty = 0;
      }
      tmp[i] = matrix[firstLineNoEmpty];
      ++firstLineNoEmpty;
    }
    
    int[][] tmp2 = new int[tmp.length][tmp[0].length];
    for(int i = 0; i < tmp.length; ++i){
      for(int j = 0; j < tmp[i].length; ++j){
        if(firstColNoEmpty == tmp[i].length){
          firstColNoEmpty = 0;
        }
        tmp2[i][j] = tmp[i][firstColNoEmpty];
        ++firstColNoEmpty;
      }
      firstColNoEmpty = prevValue;
    }
    
    return tmp2;
  }

  public void tryMove(int newX, int newY) {
    if( newX >= 0 && newX <= ( 10 - getMaxX(representation) ) ){
      curX = newX;
    }
    if( newY < ( 20 - getMaxY(representation) ) ){
      curY = newY;
    }
  }
  
  public boolean tryCollision(int[][] g, int x, int y){
  	boolean res = false;
  	
  	for(int i=0; i<4; ++i) {
  		for (int j=0; j<4; ++j) {
        int value = representation[i][j];
        //On teste s'il y a déjà un élément dans la grid
        if( value > 0 && g[y + i][x + j] == 1){
          return true;
        }
  		}
  	}
  	
  	return res;
  }
}
