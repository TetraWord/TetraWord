package GameEngine;

public class Player {
	private final BoardGame boardGame = new BoardGame();
	private final int number;
  private final Thread thread;
  private double score;
  private int numLinesRemoved;
  private Shape shapeStocked;
	
	public Player( int nb ){
    score = 0;
    numLinesRemoved = 0;
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
  
  public static void main( String[] args ){
    Player p1 = new Player(1);
    Player p2 = new Player(2);
    Thread t = new Thread(new RunPlayer(p1));
    Thread t2 = new Thread(new RunPlayer(p2));
    t.start();
    t2.start();
  }

  public int getNumber() {
    return number;
  }
}
