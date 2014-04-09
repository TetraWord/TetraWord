
package GraphicEngine;

import GameEngine.Brick;
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
  
  private final Shape model;
	private String brick;
	protected final Brick2D[][] compositionBrick2D;
  
  public Shape2D(Shape s){
    this.model = s;
    
		int[][] representation = model.getRepresentation();
		Brick[][] composition = model.getComposition();
		compositionBrick2D = new Brick2D[composition.length][composition[0].length];
		for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					if (representation[i][j] > 0) {
						compositionBrick2D[i][j] = new Brick2D(composition[i][j]);
					} else {
						compositionBrick2D[i][j] = null;
					}
				}
			}
		
		
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
		
		int top = y;
		int left = x;
		int sizeBrick = (int)(35 * ratio);
		Color c = model.getColor();
    BufferedImage monImage = getBrickImage(c);
			int[][] representation = model.getRepresentation();
			for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					if (representation[i][j] > 0) {
						if (compositionBrick2D[i][j] != null) {
							compositionBrick2D[i][j].draw(g, j * sizeBrick + left, i * sizeBrick + top, monImage);
						}
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
