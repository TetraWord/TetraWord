package ContextManager;

import GameEngine.CurrentShape;
import GameEngine.GameEngine;
import GameEngine.Grid;
import GameEngine.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * <b> Config1 is an KeyEventListener class for the player 1. </b>
 * <p>
 * Config1 contains the player who plays with it. </p>
 * <p>
 * It extends from KeyAdapter, and override the keyPressed method </p>
 *
 * @see Player
 */
public class Config1 extends KeyAdapter {

  /**
   * The player of the config. Not modifiable.
   */
  private final Player p;

  /**
   * Config1 constructor.
   * <p>
   * At the construct of a Config1's object, we associate the player passed in
   * param at it.
   * </p>
   *
   * @param p The player of the config.
   */
  public Config1(Player p) {
    this.p = p;
  }

  /**
   * The overriden method keyPressed.
   * <p>
   * Listen which key is pressed on the keyboard.
   * </p>
   * <p>
   * According to the pressed key, some methods are called on the player or the
   * manager of the game :
   * <ul>
   * <li> UP -> rotate the player's current shape </li>
   * <li> DOWN -> move down the player's current shape and add score to the
   * player </li>
   * <li> LEFT -> move left the player's current shape </li>
   * <li> RIGHT -> move right the player's current shape </li>
   * <li> NUMPAD0 -> drop down the player's current shape </li>
   * <li> NUMPAD1 -> launch the worddle mode for the player if he can </li>
   * <li> NUMPAD2 -> stock the player's current shape if he can </li>
   * <li> ENTER -> if the player is in Tetris mode launch his modifier </li>
   * <li> ENTER -> if the player is in Worddle mode or Anagram mode, validate
   * the input word </li>
   * <li> P -> set the manager of the game in pause mode </li>
   * </ul>
   * </p>
   *
   * @see Player#rotate()
   * @see Player#down(int) 
   * @see Player#addToScore(int) 
   * @see Player#left()
   * @see Player#right()
   * @see Player#dropDown()
   * @see ContextManager#setPause()
   * @param e The key pressed. Can get the key code with <b> getKeyCode() </b>
   * method.
   */
  @Override
  public void keyPressed(KeyEvent e) {

    if (ContextManager.getInstance().isPaused) {
      return;
    }

    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        p.rotate();
        break;

      case KeyEvent.VK_DOWN:
        p.down(1);
        p.addToScore(1);
        break;

      case KeyEvent.VK_LEFT:
        p.left();
        break;

      case KeyEvent.VK_RIGHT:
        p.right();
        break;

      case KeyEvent.VK_NUMPAD0:
        p.dropDown();
        break;

      case KeyEvent.VK_ENTER:
        if ((p.isAnagram() || p.isWorddle()) && p.getWord().length() > 0) {
          p.setWordFinish();
          p.getGrid().setNoLastBrickClicked();
        } else if (p.hasModifier() && p.isTetris()) {
          p.activeModifier();
        }
        break;

      case KeyEvent.VK_NUMPAD1:
        if (!GameEngine.getInstance().isPlayersInWordMode() && p.canWorddle()) {
          p.switchToWorddle(true);
          GameEngine.getInstance().beginWorddleTimer(p);
          p.stockCurrentShape();
          p.addNewChar(p.getBoardGame().clickedOneBrick());
        }
        break;

      case KeyEvent.VK_NUMPAD2:
        Grid grid = p.getGrid();
        CurrentShape cs = grid.getCurrentShape();
        if (!p.hasShapeStocked()) {
          p.stockShape(cs);
          p.getBoardGame().launchNextShape();
        } else if (p.canSwitchShape()) {
          grid.setCurrentShape(new CurrentShape(p.useShapeStocked()));
          p.stockShape(cs);
        }
        break;

      case KeyEvent.VK_P:
        ContextManager.getInstance().setPause();
        break;
    }

  }
}
