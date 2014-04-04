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
		while(this.player.getBoardGame().getPlay()){
			System.out.println(this.player.getBoardGame().getPlay());
			player.down();
			try {
				this.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(RunPlayer.class.getName()).log(Level.SEVERE, null, ex);
			}
	  }
		System.out.println("you loose");
		// TODO: on ne doit plus pouvoir utiliser le clavier
	}
  
}
