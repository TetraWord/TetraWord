package GraphicEngine;

import GameEngine.Brick;
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
import javax.imageio.ImageIO;

public class Brick2D {

	private final Brick model;
	private static String brickImg;

	public Brick2D(Brick model) {
		this.model = model;
	}

	public static void setBrickImage(String brickImg) {
		Brick2D.brickImg = brickImg;
	}

	public void draw(Graphics g, int top, int left, double ratio, boolean shadow) {

		Color c;
		if (model.isClicked()) {
      if(model.isDoubleClicked()){
        c = Color.black;
      }else{
        c = Color.gray;
      }
		} else {
			c = model.getColor();
		}
		BufferedImage monImage = getBrickImage(c);

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

		String letter = Character.toString(model.getLetter());
		String toUpperCase = letter.toUpperCase();

		g.drawImage(monImage, top, left, null);

		if (shadow) {
			Graphics2D g2d = (Graphics2D) g;
			Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2d.setComposite(comp);
			g2d.setPaint(Color.black);
			g2d.setFont(new Font("Arial", Font.BOLD, (int) (ratio * 20)));
			g2d.drawString(toUpperCase, top + (int) (ratio * 10), left + (int) (ratio * 25));
			comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			g2d.setComposite(comp);

		} else {
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, (int) (ratio * 20)));
			g.drawString(toUpperCase, top + (int) (ratio * 10), left + (int) (ratio * 25));
		}
	}

	public BufferedImage getBrickImage(Color myColor) {
		try {
			BufferedImage monImage = ImageIO.read(new File(brickImg));
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

	public BufferedImage getBrickShadow(Color myColor) {
		try {
			BufferedImage monImage = ImageIO.read(new File(brickImg));
			WritableRaster trame = monImage.getRaster();
			ColorModel color = monImage.getColorModel();

			for (int i = 0; i < 35; ++i) {
				for (int j = 0; j < 35; ++j) {
					/*Get alpha of the image*/
					Object pixel = trame.getDataElements(i, j, null);
					int alpha = color.getAlpha(pixel);
					if (alpha >= 110) {
						alpha -= 110;
					} else {
						alpha = 0;
					}
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
