/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ContextManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEventListener implements ActionListener, KeyListener{

  public GameEventListener(){}
  
  @Override
  public void actionPerformed(ActionEvent e) {
	  
  }
  
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

  @Override
  public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
  }

  @Override
  public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
  }
}
