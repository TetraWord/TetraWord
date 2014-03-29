package GameEngine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RunPlayer extends Thread {
  
  private Player player;
  
  public RunPlayer(Player p){
    this.player = p;
  }
  
  @Override
  public void run() {
    while(true){
      player.down();
      try {
        this.sleep(1000);
      } catch (InterruptedException ex) {
        Logger.getLogger(RunPlayer.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
}
