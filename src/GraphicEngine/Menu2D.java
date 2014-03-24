
package GraphicEngine;

import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Menu2D extends JPanel {
  private Button2D[] buttons;
  private int capacity;
  private int current;
  
  public Menu2D() {
    //Panel settings
    this.setLayout(null);
    
    //Menu settings
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
  
  public Button2D[] getButtons(){
    return buttons;
  }
  
  public Button2D getAt( int index ){
    return buttons[index];
  }
  
  public void defineMainMenu(){
    buttons = new Button2D[4];
    capacity = 4;
    current = 0;
    int sx = 150, sy = 60;
    int x = WINDOW_WIDTH/2 - sx/2;
    int y = 50;
    int step_y = y+sy;
    this.add(new Button2D("Start new game", x, y, sx, sy));
    y = y + step_y;
    this.add(new Button2D("Load game", x, y, sx, sy));
    y = y + step_y;
    this.add(new Button2D("Options", x, y, sx, sy));
    y = y + step_y;
    this.add(new Button2D("Exit", x, y, sx, sy));
  }
  
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  } 
  
}
