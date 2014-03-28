
package GraphicEngine;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class Grid2D extends JPanel {

    private Shape2D currentShape = null;

    public Grid2D() {
        currentShape = new Shape2D();
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {

        try {
            /*Try to load background image from chosen design*/
            Image img = ImageIO.read(new File("media/Design/paper/background.jpg"));
            g.drawImage(img, 0, 0, this);
        } catch (IOException e) {
            /*Load background image from default design*/
             e.printStackTrace();
        }
        
        //Shape draw
        if (currentShape != null) {
            currentShape.paintComponent(g);
        }
        }

}
