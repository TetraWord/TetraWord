package GameEngine;

import ContextManager.ContextManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunPlayer implements Runnable {

  private final Player player;

  public RunPlayer(Player p) {
    this.player = p;
  }

  @Override
  public void run() {
    while (!this.player.isFinish()) {
      if (!ContextManager.getInstance().isPaused && !player.isAnagram()) {
        player.down();
        try {
          Thread.sleep(1000 / player.getLevel());
        } catch (InterruptedException ex) {
          Logger.getLogger(RunPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (player.isAnagram()) {
        player.doAnagram();
      }
    }
    System.out.println("you loose");
    // TODO: on ne doit plus pouvoir utiliser le clavier
  }

}
