
package GraphicEngine;

import java.awt.Graphics;
import javax.swing.JPanel;

class Menu2D extends JPanel {
  private Button2D[] buttons;
  private int capacity;
  private int current;
  
  public Menu2D() {
     capacity = 0;
     current = 0;
  }
  
  public void add(Button2D newButton){
    if( current < capacity ){
      buttons[current] = newButton;
      super.add(buttons[current]);
      ++current;
    }
  }
  
  public void defineMainMenu(){
    buttons = new Button2D[4];
    capacity = 4;
    current = 0;
    this.add(new Button2D("Start new game", 50, 100, 100, 30));
    this.add(new Button2D("Exit", 150, 200, 100, 30));
  }
  
  @Override
  public void paintComponent(Graphics g){
    
  } 
  
}
