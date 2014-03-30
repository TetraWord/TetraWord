package GameEngine;

public class Player {
	private final BoardGame boardGame;
	private final int number;
  private double score;
  private int numLinesRemoved;
  private Shape shapeStocked;
	
	public Player( int nb ){
    boardGame = new BoardGame(nb);
    score = 0;
    numLinesRemoved = 0;
		number = nb;
	}
  
  public void addShapeStocked(Shape s){
    if( shapeStocked != null ){
      shapeStocked = s;
    }
  }
  
  public Shape useShapeStocked(){
    Shape s = shapeStocked;
    shapeStocked = null;
    return s; //vérifier si ca ne retourne pas toujours null... 
  }
  
  public int getNumber() {
    return number;
  }

  public synchronized void down() {
    CurrentShape s = getCurrentShape();
    
    if ( !s.tryMove(s.getX(), s.getY()+1) ) {
    	Grid g = boardGame.getGrid();
    	int[][] tmp = g.getTGrid(); 
    	
    	for(int i=0; i<4; ++i) {
    		for (int j=0; j<4; ++j) {
    			tmp[s.getX() + i][s.getY() + j] = s.representation[i][j];
    		}
    	}
    	
    	g.setTGrid(tmp);
    }
  }

  public void left() {
    CurrentShape s = getCurrentShape();
    s.tryMove(s.getX() - 1, s.getY());
  }

  public void right(){
    CurrentShape s = getCurrentShape();
    s.tryMove(s.getX() + 1, s.getY());
  }

  public void rotate(){
    CurrentShape s = getCurrentShape();
    s.rotateLeft();
  }
  
  public BoardGame getBoardGame() {
    return boardGame;
  }
  
  private CurrentShape getCurrentShape(){
    return boardGame.getGrid().getCurrentShape();
  }
}
