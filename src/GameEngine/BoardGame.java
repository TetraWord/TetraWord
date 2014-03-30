package GameEngine;

public class BoardGame {
	private final Grid grid;
  private final int nb;
  private boolean doUpdate = false;
  
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

  public boolean getUpdate() {
    return doUpdate;
  }

  public void setUpdate(boolean b) {
    doUpdate = b;
  }
}
