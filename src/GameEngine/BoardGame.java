package GameEngine;

public class BoardGame {
	private final Grid grid;
  
  public BoardGame(){
    grid = new Grid();
  }
  
  public Grid getGrid(){
    return grid;
  }
}
