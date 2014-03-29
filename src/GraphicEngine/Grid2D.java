
package GraphicEngine;

import GameEngine.CurrentShape;
import java.awt.Graphics;
import javax.swing.JPanel;

class Grid2D extends JPanel {

    private Shape2D currentShape = null;

    public Grid2D() {
        this.setVisible(true);
    }

    public void setShape2D(CurrentShape s){
      currentShape = new Shape2D(s);
    }
    
    @Override
    public void paintComponent(Graphics g) {     
        //Shape draw
        if (currentShape != null) {
            currentShape.paintComponent(g);
        }
    }

}