package GameEngine;

public class Player implements Runnable {
	private final BoardGame boardGame = new BoardGame();
	private final int number;
  private final Thread thread;
  private double score;
  private Shape shapeStocked;
	
	public Player(int nb){
    score = 0;
		number = nb;
    thread = new Thread();
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

  @Override
  public void run() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
