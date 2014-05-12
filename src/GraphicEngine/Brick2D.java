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

/**
 * <b> Brick2D is the graphic representation of a brick. </b>
 * <p>
 * The Brick2D contains:
 * <ul>
 * <li> A Brick model. </li>
 * <li> The pathname of the image file of the brick. </li>
 * </ul>
 *
 * </p>
 *
 * @see Brick
 */
public class Brick2D {

  /**
   * The logical represention of a brick.
   *
   * @see Brick
   */
  private final Brick model;

  /**
   * The pathname of the image file of all bricks.
   */
  private static String brickImg;

  /**
   * Brick2D constructor.
   *
   * @param model Logical represention of the brick
   */
  public Brick2D(Brick model) {
    this.model = model;
  }

  /**
   * Initialize the image file pathname.
   *
   * @param brickImg Image file pathname
   */
  public static void setBrickImage(String brickImg) {
    Brick2D.brickImg = brickImg;
  }

  /**
   * Draw the Brick representation.
   *
   * @param g Graphics to draw on
   * @param top Top position
   * @param left Left position
   * @param ratio Ratio of drawing
   * @param shadow Is a shadow ?
   */
  public void draw(Graphics g, int top, int left, double ratio, boolean shadow) {

    /* Set the color to use for the brick */
    Color c;
    if (model.isClicked()) {
      if (model.isDoubleClicked()) {
        c = Color.black;
      } else if (model.canBeInANewWord()) {
        c = Color.gray;
      } else {
        c = Color.lightGray;
      }
    } else {
      c = model.getColor();
    }

    /* Colorize the brick image */
    BufferedImage monImage = colorizeBrickImage(c);

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

    /* Draw shadow */
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
      /* Draw text */
      g.setColor(Color.black);
      g.setFont(new Font("Arial", Font.BOLD, (int) (ratio * 20)));
      g.drawString(toUpperCase, top + (int) (ratio * 10), left + (int) (ratio * 25));
    }
  }

  /**
   * Colorize the brick image.
   *
   * @param myColor Color to colorize the brick image
   * @return Color to colorize the brick image
   */
  private BufferedImage colorizeBrickImage(Color myColor) {
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

}
