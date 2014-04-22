package ContextManager;

import GameEngine.GameEngine;
import GameEngine.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        p.down();
        p.addToScore(1);
        break;

      case KeyEvent.VK_Q:
        p.left();
        break;

      case KeyEvent.VK_D:
        p.right();
        break;

      case KeyEvent.VK_2:
        if ( (p.isAnagram() || p.isWorddle()) && p.getWord().length() > 0) {
          p.setWordFinish();
          p.getBoardGame().setNoLastBrickClicked();
        }
        break;
        
      case KeyEvent.VK_E:
        if(!GameEngine.getInstance().isPlayersInWordMode()){
          p.switchToWorddle(true);
          GameEngine.getInstance().beginWorddleTimer(p);
          p.stockCurrentShape();
          p.addNewChar(p.getBoardGame().clickedOneBrick());
        }
        break;

      case KeyEvent.VK_F:
        p.dropDown();
        break;
    }

  }
}
