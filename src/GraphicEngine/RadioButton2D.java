package GraphicEngine;

import javax.swing.JRadioButton;

/**
 * <b> RadioButton2D is a radio button permitting to select bricks in design
 * menu. </b>
 * <p>
 * RadioButton2D inherits from JRadioButton.</p>
 * <p>
 * RadioButton2D contains:
 * <ul>
 * <li> A name. </li>
 * <li> The image pathname of the corresponding brick.
 * </li>
 * </ul>
 *
 * </p>
 *
 * @see JRadioButton
 */
public class RadioButton2D extends JRadioButton {

  /**
   * Name of the button.
   */
  private final String name;
  /**
   * Pathname of the brick image.
   */
  private final String img;

  /**
   * RadioButton2D constructor. Initialize attributes.
   *
   * @param name Name of the button
   * @param img Pathname of the brick image
   */
  public RadioButton2D(String name, String img) {
    super(name);
    this.name = name;
    this.img = img;
  }

  /**
   * Get the name of the button.
   *
   * @return Name of the button
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Get the pathname of the brick image.
   *
   * @return Pathname of the brick image
   */
  public String getImage() {
    return img;
  }
}
