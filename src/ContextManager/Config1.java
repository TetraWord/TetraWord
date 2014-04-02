
package ContextManager;

import GameEngine.Player;
import GraphicEngine.GraphicEngine;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Config1 extends KeyAdapter {
  
  private final Player p;
  
  public Config1(Player p){
    this.p = p;
  }
  
  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
        System.out.println("Up pressed");
        p.rotate();
	      break;
	            
	    case KeyEvent.VK_DOWN:
        //System.out.println("Down pressed");
        p.down();
        break;
      
      case KeyEvent.VK_LEFT:
        System.out.println("Left pressed");
        p.left();
        break;
      
      case KeyEvent.VK_RIGHT:
        System.out.println("Right pressed");
        p.right();
        break;
        
      case KeyEvent.VK_NUMPAD0:
        System.out.println("0 pressed");
        p.stockShape();
        break;
		}
    
    repaint();
  }
  
  private void repaint(){
    GraphicEngine g = GraphicEngine.getInstance();
    g.renderFrame();
    
  }
}
