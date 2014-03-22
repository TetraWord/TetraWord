
package GraphicEngine;

import javax.swing.JButton;

public class Button2D extends JButton {
  
  public Button2D(String name, int x, int y, int sx, int sy){
    super(name);
    this.setBounds(x, y, sx, sy);
    this.setFocusable(false);
  }

}
