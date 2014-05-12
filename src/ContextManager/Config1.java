package ContextManager;

import GameEngine.CurrentShape;
import GameEngine.GameEngine;
import GameEngine.Grid;
import GameEngine.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * <b> Config1 is an KeyEventListener class for the Player 1. </b>
 * <p>
 * Config1 contains the Player who plays with it. </p>
 *
 * @see Player
 */
public class Config1 extends KeyAdapter {

  /**
   * The Player of the config. Not modifiable.
   */
  private final Player p;

  /**
   * Config1 constructor.
   * <p>
   * At the construct of a Config1's object, we associate the Player's param to
   * the Config.
   * </p>
   *
   * @param p The Player of the config.
   */
  public Config1(Player p) {
    this.p = p;
  }

  /**
   * Listen which key is pressed on the keyboard.
   * <p>
   * According to the pressed key, some methods are called on the Player or the
   * manager of the game :
   * <ul>
   * <li> UP -> rotate the Player's current shape </li>
   * <li> DOWN -> move down the Player's current shape and add score to the
   * Player </li>
   * <li> LEFT -> move left the Player's current shape </li>
   * <li> RIGHT -> move right the Player's current shape </li>
   * <li> NUMPAD0 -> drop down the Player's current shape </li>
   * <li> NUMPAD1 -> launch the Worddle mode for the Player if he can </li>
   * <li> NUMPAD2 -> stock the Player's current shape if he can </li>
   * <li> ENTER -> if the Player is in Tetris mode launch his modifier </li>
   * <li> ENTER -> if the Player is in Worddle mode or Anagram mode, validate
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
        if ((p.isAnagram() || p.isWorddle()) && p.getWord().length() > 1) {
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
          p.addNewChar(p.getGrid().clickedOneBrick());
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
