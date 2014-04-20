package GraphicEngine;

import javax.swing.JButton;

public class BrickButton extends JButton {

  private final int posX;
  private final int posY;

  public BrickButton(int posX, int posY) {
    this.posX = posX;
    this.posY = posY;

    int sizeBrick = 35;

    setBounds(posX * sizeBrick, posY * sizeBrick, sizeBrick, sizeBrick);
    setBorder(null);
    setFocusable(false);
    setOpaque(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
  }

  public int getPosX() {
    return posX;
  }

  public int getPosY() {
    return posY;
  }
}
