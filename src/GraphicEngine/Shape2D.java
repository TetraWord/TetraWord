
package GraphicEngine;

import GameEngine.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Shape2D {
  private Shape model;
  
  public Shape2D(Shape model){
    this.model = model;
  }
  
  public void paintComponent (Graphics g){
    /*Drawing of a brick whith a fixed color*/
    try {
      BufferedImage monImage = ImageIO.read(new File("media/Design/paper/brick.png"));
      WritableRaster trame = monImage.getRaster();
      ColorModel color = monImage.getColorModel();
      Color myColor = new Color(model.getR(), model.getG(), model.getB());
      int rgb = myColor.getRGB();
      System.out.println(rgb);
      Object couleurBlanc = color.getDataElements(rgb, null);

      for (int i = 0; i < 35; ++i) {
        for (int j = 0; j < 35; ++j) {
          int alpha = monImage.getRGB(i, j);
          if (alpha < 0) {
            trame.setDataElements(i, j, couleurBlanc);
          }
        }
      }
      g.drawImage(monImage, 50, 200, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
