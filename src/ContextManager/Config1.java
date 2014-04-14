package ContextManager;

import GameEngine.Player;
import GraphicEngine.GraphicEngine;
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
        p.down();
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
        if(p.isAnagram()){
          p.switchToAnagram(false);
          p.getBoardGame().setAllowClick(false);
        }
        break;

      case KeyEvent.VK_P:
        ContextManager.getInstance().setPause();
        break;
    }

    repaint();
  }

  private void repaint() {
    GraphicEngine g = GraphicEngine.getInstance();
    g.renderFrame();

  }
}
