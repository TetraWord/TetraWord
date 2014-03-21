package GraphicEngine;

import javax.swing.JFrame;

/***
 * Define the main window of the application
 */

public class Window extends JFrame{
  
  public Window(int w, int h){
    //Window settings
    this.setTitle("TetraWord");
    this.setSize(w, h);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);  
    this.setContentPane(new BoardGame2D());
    this.setVisible(true);

  }
  
}
