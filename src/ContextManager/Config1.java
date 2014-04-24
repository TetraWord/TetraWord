package ContextManager;

import GameEngine.CurrentShape;
import GameEngine.GameEngine;
import GameEngine.Grid;
import GameEngine.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Config1 extends KeyAdapter {

  private final Player p;

  public Config1(Player p) {
    this.p = p;
  }

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
        if ( (p.isAnagram() || p.isWorddle()) && p.getWord().length() > 0) {
          p.setWordFinish();
          p.getBoardGame().setNoLastBrickClicked();
        } else if(p.hasModifier()){
          p.activeModifier();
        }
        break;

      case KeyEvent.VK_NUMPAD1:
        if(!GameEngine.getInstance().isPlayersInWordMode() && p.canWorddle()){
          p.switchToWorddle(true);
          GameEngine.getInstance().beginWorddleTimer(p);
          p.stockCurrentShape();
          p.addNewChar(p.getBoardGame().clickedOneBrick());
        }
        break;
        
      case KeyEvent.VK_NUMPAD2:
        Grid grid = p.getBoardGame().getGrid();
        CurrentShape cs = grid.getCurrentShape();
        if (!p.hasShapeStocked()) {
          p.stockShape(cs);
          p.getBoardGame().launchNextShape();
        } else {
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
