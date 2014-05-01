package GraphicEngine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import GameEngine.Modifier;

public class Modifier2D {
	private final Modifier model;
	private static String modifierImg;
	
	public Modifier2D(Modifier model) {
		this.model = model;
	}
	
	public static void setModifierImage(String modifierImg) {
		Modifier2D.modifierImg = modifierImg;
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double ratio) {
		int top = 100 + offsetY;
		int left = 35 + offsetX;
		int sizeBrick = 35;
		Color c = Color.RED;
		BufferedImage monImage = getModifierImage(c);

		if (ratio != 1) {
			/*Resize image*/
			BufferedImage before = monImage;
			int w = before.getWidth();
			int h = before.getHeight();
			BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(ratio, ratio);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(before, after);
			monImage = after;
		}

		g.drawImage(monImage, model.getX() * sizeBrick + left, model.getY() * sizeBrick + top, null);
		g.setColor(Color.black);
	}

	public BufferedImage getModifierImage(Color myColor) {
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
