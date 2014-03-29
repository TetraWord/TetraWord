
package ContextManager;

import GameEngine.Player;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Config2 extends KeyAdapter {
  
  private Player p;
  
  public Config2(Player p){
    this.p = p;
  }
  
  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
			case KeyEvent.VK_Z:
        System.out.println("Up pressed");
        p.rotate();
	      break;
	            
	    case KeyEvent.VK_S:
        System.out.println("Down pressed");
        p.down();
        break;
      
      case KeyEvent.VK_Q:
        p.left();
        System.out.println("Left pressed");
        
        break;
      
      case KeyEvent.VK_D:
        p.right();
        System.out.println("Right pressed");
        break;
		}
  }
}