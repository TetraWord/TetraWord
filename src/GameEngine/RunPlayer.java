package GameEngine;

import ContextManager.ContextManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <b> Runnable implementation for the Player. </b>
 * <p>
 * Automatic Player's movement / switch mode. </p>
 *
 * @see Player.
 */
public class RunPlayer implements Runnable {

  private final Player player;

  /**
   * Constructor.
   *
   * @param p The Player who played
   */
  public RunPlayer(Player p) {
    this.player = p;
  }

  /**
   * To start the Player Thread.
   * <p>
   * When in Tetris mode, move down the Shape according to the speed fall. </p>
   * <p>
   * When in Worddle mode, call the doWorddle method of the Player. </p>
   * <p>
   * When in Anagram mode, call the doAnagram method of the Player. </p>
   *
   */
  @Override
  public void run() {
    player.updateObserver("Bienvenue " + player.getName());
    while (!this.player.isFinish()) {
      if (!ContextManager.getInstance().isPaused && player.isTetris()) {
        player.down(1);
        try {
          Thread.sleep(player.getSpeedFall());
        } catch (InterruptedException ex) {
          Logger.getLogger(RunPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (player.isAnagram()) {
        player.doAnagram();
      } else if (player.isWorddle()) {
        player.doWorddle();
      }

      player.updateObserver(null);
    }
    ContextManager.getInstance().stop();
  }

}
