
package GraphicEngine;

import GameEngine.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.imageio.ImageIO;

public class Shape2D {
  
  private Shape model;
	private String brick;
  
  public Shape2D(Shape s){
    this.model = s;
    
    Properties prop = new Properties();
		InputStream input = null;
		/*Get brick image from file myConf*/
		try {

			input = new FileInputStream("conf/myConf.properties");

			// load a properties file
			prop.load(input);

			brick = prop.getProperty("brick");

		} catch (IOException ex) {
			/*Default background*/
			brick = "media/Design/paper/brick.jpg";
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
  }
  
  public void draw(Graphics g, int x, int y, double ratio) {
		/*Drawing of a brick whith the color of the model */
      Color c = model.getRGB();
      BufferedImage monImage = getBrickImage(c);

      //Draw the shape
			//70 en x et 135 en y pour le coin haut gauche
			int top = y;
			int left = x;
			int sizeBrick = (int)(35 * ratio);
			int[][] representation = model.getRepresentation();
			for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					if (representation[i][j] > 0) {
						g.drawImage(monImage, j * sizeBrick + left, i * sizeBrick + top, null);
					}
				}
			}
		
	}
  
  public BufferedImage getBrickImage (Color myColor){
    try {
			BufferedImage monImage = ImageIO.read(new File(brick));
			WritableRaster trame = monImage.getRaster();
			ColorModel color = monImage.getColorModel();

			for (int i = 0; i < 35; ++i) {
				for (int j = 0; j < 35; ++j) {
					/*Get alpha of the image*/
					Object pixel = trame.getDataElements(i, j, null);
					int alpha = color.getAlpha(pixel);
					/*Create new color with alpha of the image*/
					myColor = new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(), alpha);
					int rgb = myColor.getRGB();
					Object couleur = color.getDataElements(rgb, null);
					trame.setDataElements(i, j, couleur);
				}
			}    
      return monImage;
    } catch (IOException e) {
			e.printStackTrace();
      return null;
		}
  }
}
