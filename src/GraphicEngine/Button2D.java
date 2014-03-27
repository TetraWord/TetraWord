
package GraphicEngine;

import ContextManager.ContextManager;
import javax.swing.JButton;

public class Button2D extends JButton {
  private final String name;
  
  public Button2D(String name, int x, int y, int sx, int sy){
    super(name);
    this.name = name;
    this.setBounds(x, y, sx, sy);
    this.setFocusable(false);
    this.addActionListener( ContextManager.getInstance().getMenuListener() );
  }
  
  @Override
  public String getName(){
    return name;
  }

}
