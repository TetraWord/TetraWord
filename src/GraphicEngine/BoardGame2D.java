package GraphicEngine;

import ContextManager.ContextManager;
import java.awt.Graphics;
import javax.swing.JPanel;

/***
 * Define one board for a player with it's own grid
 */

public class BoardGame2D extends JPanel {
  private final Grid2D gameGrid;
  
  public BoardGame2D(){
 
    this.addKeyListener( ContextManager.getInstance().getPlayerListener(1));
    this.addKeyListener( ContextManager.getInstance().getPlayerListener(2));
    
    gameGrid = new Grid2D ();
  }
  
  @Override
  public void paintComponent(Graphics g){
    gameGrid.paintComponent(g);
  } 
          
}
