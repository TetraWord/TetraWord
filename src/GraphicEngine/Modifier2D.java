package GraphicEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import GameEngine.CurrentModifier;

/**
 * <b> Modifier2D is the graphic representation of a CurrentModifier. </b>
 *
 * @see CurrentModifier
 * @see Modifier
 */
public class Modifier2D {

	/**
	 * The CurrentModifier model
	 */
	private final CurrentModifier model;

	/**
	 * The modifier image
	 */
	private static BufferedImage modifierImg;

	/**
	 * Modifier2D constructor
	 *
	 * @param model The CurrentModifier model
	 */
	public Modifier2D(CurrentModifier model) {
		this.model = model;
	}

	/**
	 * Set the image modifier for all modifiers
	 *
	 * @param modifierImg Pathname of the modifier image
	 */
	public static void setModifierImage(String modifierImg) {

		Color c = Color.RED;
		try {
			BufferedImage monImage = ImageIO.read(new File(modifierImg));
			WritableRaster trame = monImage.getRaster();
			ColorModel color = monImage.getColorModel();

			for (int i = 0; i < 35; ++i) {
				for (int j = 0; j < 35; ++j) {
					/*Get alpha of the image*/
					Object pixel = trame.getDataElements(i, j, null);
					int alpha = color.getAlpha(pixel);
					/*Create new color with alpha of the image*/
					c = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
					int rgb = c.getRGB();
					Object couleur = color.getDataElements(rgb, null);
					trame.setDataElements(i, j, couleur);
				}
			}
			Modifier2D.modifierImg = monImage;
		} catch (IOException e) {
			e.printStackTrace();
			Modifier2D.modifierImg = null;
		}
	}

	/**
	 * Draw the Modifier2D
	 * @param g Graphics to draw on
	 * @param offsetX Horizontal offset for shake modifier
	 * @param offsetY Vertical offset for shake modifier
	 */
	public void draw(Graphics g, int offsetX, int offsetY) {
		int top = 135 + offsetY;
		int left = 70 + offsetX;
		int sizeBrick = 35;

		g.drawImage(modifierImg, model.getX() * sizeBrick + left, model.getY() * sizeBrick + top, null);
		g.setColor(Color.black);
	}
}
