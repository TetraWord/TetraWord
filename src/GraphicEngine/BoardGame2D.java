package GraphicEngine;

import ContextManager.ContextManager;
import java.awt.Graphics;
import javax.swing.JPanel;

/***
 * Define one board for a player with it's own grid
 */

public class BoardGame2D extends JPanel {
  private final Grid gameGrid;
  
  public BoardGame2D(){
 
    this.addKeyListener( ContextManager.getInstance().getGameListener() );
    
    this.setFocusable(true);
    gameGrid = new Grid();
  }
  
  @Override
  public void paintComponent(Graphics g){
    gameGrid.paintComponent(g);
  } 
          
}
