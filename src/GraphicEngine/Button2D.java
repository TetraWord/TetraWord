package GraphicEngine;

import ContextManager.ContextManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * <b> Button2D is a JButton with our design. </b>
 * <p>
 * Button2D inherits from JButton. </p>
 * <p>
 * The BrickButton contains:
 * <ul>
 * <li> A name. </li>
 * </ul>
 * </p>
 */
public class Button2D extends JButton {

  /**
   * Button's name.
   */
  private final String name;

  /**
   * Button2D default constructor.
   *
   * @param name Name of the button
   * @param x Horizontal position
   * @param y Vertical position
   * @param sx Width
   * @param sy Heigth
   */
  public Button2D(String name, int x, int y, int sx, int sy) {
    super(name);
    this.name = name;
    this.setBounds(x, y, sx, sy);
    this.setFocusable(false);
    this.addActionListener(ContextManager.getMenuListener());

    /*Design*/
    this.setIcon(new ImageIcon("media/Design/btnBG.jpg"));

    this.setVerticalTextPosition(SwingConstants.CENTER);
    this.setHorizontalTextPosition(SwingConstants.CENTER);
    this.setForeground(Color.white);
    this.setFont(new Font("Champagne & Limousines", 30, 30));
  }

  /**
   * Delete the listener of the button.
   */
  public void removeListener() {
    this.removeActionListener(ContextManager.getMenuListener());
  }

  /**
   * Get the button's name.
   *
   * @return Name of the button
   */
  @Override
  public String getName() {
    return name;
  }
}
