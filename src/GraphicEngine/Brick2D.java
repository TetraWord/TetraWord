package GraphicEngine;

import GameEngine.Brick;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Brick2D {

  private final Brick model;

  public Brick2D(Brick model) {
    this.model = model;
  }

  public void draw(Graphics g, int top, int left, BufferedImage img, boolean shadow) {

    String letter = Character.toString(model.getLetter());
    String toUpperCase = letter.toUpperCase();

    g.drawImage(img, top, left, null);

    if (shadow) {
      Graphics2D g2d = (Graphics2D) g;

      Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
      g2d.setComposite(comp);
      g2d.setPaint(Color.black);
      g2d.setFont(new Font("Arial", Font.BOLD, 20));
      g2d.drawString(toUpperCase, top + 10, left + 25);
      comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
      g2d.setComposite(comp);

    } else {
      g.setColor(Color.black);
      g.setFont(new Font("Arial", Font.BOLD, 20));
      g.drawString(toUpperCase, top + 10, left + 25);
    }
  }

}
