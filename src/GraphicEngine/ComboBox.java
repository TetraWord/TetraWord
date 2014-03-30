
package GraphicEngine;

//import ContextManager.ContextManager;
import javax.swing.JComboBox;

public class ComboBox extends JComboBox {
	private final String name;
  
  public ComboBox(String name, String[] tab, int x, int y, int sx, int sy){
    super(tab);
    this.name = name;
    this.setBounds(x, y, sx, sy);
		/*Ajouter un écouteur*/
  }
  
  @Override
  public String getName(){
    return name;
  }
}
