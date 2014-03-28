/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContextManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerEventListener extends KeyAdapter{

  public PlayerEventListener(){}
  
  @Override
  public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				System.out.println("Up pressed");
	            break;
	            
	        case KeyEvent.VK_DOWN:
	        	System.out.println("Down pressed");
	            break;
	            
	        case KeyEvent.VK_LEFT:
	        	System.out.println("Left pressed");
	            break;
	            
	        case KeyEvent.VK_RIGHT:
	        	System.out.println("Right pressed");
	        	break;
		}
  }

}
