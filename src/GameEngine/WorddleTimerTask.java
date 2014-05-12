package GameEngine;

import java.util.TimerTask;

/**
 * <b> A timer task for the Worddle Mode. </b>
 * <p>
 * Manage the Player Worddle mode. </p>
 * <p>
 * It contains the Player affect by this timer task. </p>
 *
 */
public class WorddleTimerTask extends TimerTask {

  private final Player p;

  /**
   * Contructor. Affect the Player to the timer task.
   *
   * @param p The Player
   */
  public WorddleTimerTask(Player p) {
    this.p = p;
  }

  /**
   * Switch the Player to Tetris Mode when the Worddle timer is over, or enable
   * the Player to witch in Worddle mode if the Worddle timer is ready.
   *
   */
  @Override
  public void run() {
    if (p.isWorddle()) {
      p.switchToWorddle(false);
    } else {
      p.setWorddle(true);
    }
  }

}
