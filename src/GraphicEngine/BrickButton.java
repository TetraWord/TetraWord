package GraphicEngine;

import ContextManager.GridEventListener;
import javax.swing.JButton;

/**
 * <b> BrickButton is a JButton that permits to choose a brick in DesignMenu2D.
 * </b>
 * <p>
 * BrickButton inherits from JButton. </p>
 * <p>
 * The BrickButton contains:
 * <ul>
 * <li> The horizontal postition. </li>
 * <li> The vertical postition. </li>
 * </ul>
 *
 * </p>
 *
 * @see DesignMenu2D
 */
public class BrickButton extends JButton {

  /**
   * Horizontal position.
   */
  private final int posX;

  /**
   * Vertical position.
   */
  private final int posY;

  /**
   * BrickButton constructor. Initialize some preference for this kind of
   * button.
   *
   * @param posX Horizontal position
   * @param posY Vertical position
   * @param event The listener for this button
   */
  public BrickButton(int posX, int posY, GridEventListener event) {
    this.posX = posX;
    this.posY = posY;

    int sizeBrick = 35;

    setBounds(posX * sizeBrick, posY * sizeBrick, sizeBrick, sizeBrick);
    setBorder(null);
    setFocusable(false);
    setOpaque(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
    addMouseListener(event);
  }

  /**
   * Get the horizontal position.
   *
   * @return Horizontal position
   */
  public int getPosX() {
    return posX;
  }

  /**
   * Get the vertical position.
   *
   * @return Vertical position
   */
  public int getPosY() {
    return posY;
  }
}
