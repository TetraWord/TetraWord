package ContextManager;

import GameEngine.CurrentShape;
import GameEngine.GameEngine;
import GameEngine.Grid;
import GameEngine.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * <b> Config2 is an KeyEventListener class for the player 2. </b>
 * <p>
 * Config2 contains the player who plays with it. </p>
 * <p>
 * It extends from KeyAdapter, and override the keyPressed method </p>
 * <p>
 * Same methods as Config1 with other keys listened </p>
 *
 * @see Config1
 */
public class Config2 extends KeyAdapter {

  private final Player p;

  public Config2(Player p) {
    this.p = p;
  }

  @Override
  public void keyPressed(KeyEvent e) {

    if (ContextManager.getInstance().isPaused) {
      return;
    }

    switch (e.getKeyCode()) {
      case KeyEvent.VK_Z:
        p.rotate();
        break;

      case KeyEvent.VK_S:
        p.down(1);
        p.addToScore(1);
        break;

      case KeyEvent.VK_Q:
        p.left();
        break;

      case KeyEvent.VK_D:
        p.right();
        break;

      case KeyEvent.VK_2:
        if ((p.isAnagram() || p.isWorddle()) && p.getWord().length() > 0) {
          p.setWordFinish();
          p.getBoardGame().setNoLastBrickClicked();
        } else if (p.hasModifier() && p.isTetris()) {
          p.activeModifier();
        }
        break;

      case KeyEvent.VK_E:
        if (!GameEngine.getInstance().isPlayersInWordMode()) {
          p.switchToWorddle(true);
          GameEngine.getInstance().beginWorddleTimer(p);
          p.stockCurrentShape();
          p.addNewChar(p.getBoardGame().clickedOneBrick());
        }
        break;

      case KeyEvent.VK_R:
        Grid grid = p.getBoardGame().getGrid();
        CurrentShape cs = grid.getCurrentShape();
        if (!p.hasShapeStocked()) {
          p.stockShape(cs);
          p.getBoardGame().launchNextShape();
        } else if (p.canSwitchShape()) {
          grid.setCurrentShape(new CurrentShape(p.useShapeStocked()));
          p.stockShape(cs);
        }
        break;

      case KeyEvent.VK_F:
        p.dropDown();
        break;
    }

  }
}
