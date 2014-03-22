package GraphicEngine;

import javax.swing.JFrame;
import javax.swing.JPanel;

/***
 * Define the main window of the application
 */

public class Window extends JFrame{
  private JPanel pan;
  
  public Window(int w, int h){
    //Window settings
    this.setTitle("TetraWord");
    this.setSize(w, h);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE); 
    this.setVisible(true);
  }
  
  private void setPan( JPanel newPan ){
    pan = newPan;
  }
  
  public void defineMainMenu(){
    pan = new Menu2D();
    Menu2D menu = (Menu2D)pan;
    menu.defineMainMenu();
    this.setContentPane( menu );
    this.setVisible(true);
  }
  
  public void defineNewBoardGame(){
    pan = new BoardGame2D();
    this.setContentPane( pan );
    this.setVisible(true);
  }
  
}
