
package GraphicEngine;

import javax.swing.Icon;
import javax.swing.JRadioButton;

public class RadioButton2D extends JRadioButton{
	
	private final String name;
	
	public RadioButton2D(String name){
		super(name);
		this.name = name;
	}
  
  @Override
  public String getName(){
    return name;
  }
}
