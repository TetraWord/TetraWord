package GraphicEngine;

import GameEngine.CurrentShape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//Draw the currentShape
class Shape2D {

	private CurrentShape model;

	public Shape2D(CurrentShape model) {
		this.model = model;
	}

	public void paintComponent(Graphics g) {
		/*Drawing of a brick whith the color of the model */
		try {
			BufferedImage monImage = ImageIO.read(new File("media/Design/paper/brick.png"));
			WritableRaster trame = monImage.getRaster();
			ColorModel color = monImage.getColorModel();

			for (int i = 0; i < 35; ++i) {
				for (int j = 0; j < 35; ++j) {
					/*Get alpha of the image*/
					Object pixel = trame.getDataElements(i, j, null);
					int alpha = color.getAlpha(pixel);
					/*Create new color with alpha of the image*/
					Color myColor = new Color(model.getR(), model.getG(), model.getB(), alpha);
					int rgb = myColor.getRGB();
					Object couleur = color.getDataElements(rgb, null);
					trame.setDataElements(i, j, couleur);
				}
			}

      //Draw the shape
			//70 en x et 135 en y pour le coin haut gauche
			int top = 135;
			int left = 70;
			int sizeBrick = 35;
			int[][] representation = model.getRepresentation();
			for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					if (representation[i][j] > 0) {
						g.drawImage(monImage, (j + model.getX()) * sizeBrick + left, (i + model.getY()) * sizeBrick + top, null);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
