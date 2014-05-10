package GraphicEngine;

import ContextManager.ContextManager;
import javax.swing.JComboBox;

/**
 * <b> ComboBox is a JComboBox with our design </b>
 * <p> Button2D inherits from JComboBox</p>
 * <p>
 * The BrickButton contains :
 * <ul>
 * <li> A name</li>
 * </ul>
 * 
 * </p>
 *
 */
public class ComboBox extends JComboBox {

	/**
	 * Name of the ComboBox
	 */
  private final String name;

	/**
	 * ComboBox default constructor 
	 * @param name Name of the ComboBox
	 * @param tab Liste of String to show
	 * @param x Hozizontal position
	 * @param y Vertical position
	 * @param sx Width
	 * @param sy Height
	 */
  public ComboBox(String name, String[] tab, int x, int y, int sx, int sy) {
    super(tab);
    this.name = name;
    this.addItemListener(ContextManager.getItemListener());
    this.setBounds(x, y, sx, sy);
  }

	/**
	 * Get the name of the ComboBox
	 * @return Name of the ComboBox
	 */
  @Override
  public String getName() {
    return name;
  }
}
