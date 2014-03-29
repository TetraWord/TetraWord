package GameEngine;

public class BoardGame {
	private final Grid grid;
  private final int nb;
  
  public BoardGame(int nb){
    this.nb = nb;
    grid = new Grid();
  }
  
  public Grid getGrid(){
    return grid;
  }
  
  public int getNb(){
    return nb;
  }
}
