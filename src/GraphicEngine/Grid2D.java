
package GraphicEngine;

import static GraphicEngine.GraphicEngine.WINDOW_HEIGHT;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Graphics;
import javax.swing.JPanel;

class Grid2D extends JPanel {
  
  private Shape2D currentShape;
  
  public Grid2D(){}
  
  
  @Override
  public void paintComponent(Graphics g){
    int step = 30;
    int marge = 30;
    int height = WINDOW_HEIGHT - marge;
    int width = WINDOW_WIDTH;
    int offsetWidth = width - 11*step;
    int offsetHeight = height - 21*step;
    
    for( int i = marge; i <= width - offsetWidth; i = i + step){
      for( int j = offsetHeight; j <= height; j = j + step){
        //horizontal lines
        g.drawLine(marge, j, width - offsetWidth, j);
        //vertical lines
        g.drawLine(i, offsetHeight, i, height - marge);
      }
    }
  }
  
}
