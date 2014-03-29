package GameEngine;

public class Player {
	private final BoardGame boardGame;
	private final int number;
  private double score;
  private int numLinesRemoved;
  private Shape shapeStocked;
	
	public Player( int nb ){
    boardGame = new BoardGame();
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

  public void up() {
    
  }

  public void down() {
  }

  public void left() {
  }

  public void right() {
  }

  public BoardGame getBoardGame() {
    return boardGame;
  }
}
