package GameEngine;

public class RunPlayer implements Runnable {
  
  private Player player;
  
  public RunPlayer(Player p){
    this.player = p;
  }
  
  @Override
  public void run() {
    while(true){
      System.out.println("Coucou c'est player "+player.getNumber());
    }
  }
  
}
