package GraphicEngine;

import ContextManager.ContextManager;
import GameEngine.BoardGame;
import static GraphicEngine.GraphicEngine.WINDOW_HEIGHT;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * *
 * Define one board for a player with it's own grid
 */
public class BoardGame2D extends JPanel {

  private final Grid2D gameGrid;
  private final BoardGame model;

  public BoardGame2D(BoardGame model) {
    this.model = model;
    this.setSize(650,889);
    this.addKeyListener(ContextManager.getInstance().getPlayerListener(model.getNb()));
  
    gameGrid = new Grid2D();
    
    setShapeToGrid2D();
  }

  public void setShapeToGrid2D(){
    gameGrid.setShape2D(model.getGrid().getCurrentShape());
  }
  
  @Override
  public void paintComponent(Graphics g) {
    
    try {
      /*Try to load background image from chosen design*/
      Image img = ImageIO.read(new File("media/Design/paper/background.jpg"));
      int offset = model.getNb();
      g.drawImage(img, 0, 0, this);
    } catch (IOException e) {
      /*Load background image from default design*/
      e.printStackTrace();
    }
    gameGrid.paintComponent(g);
  }
  
}
