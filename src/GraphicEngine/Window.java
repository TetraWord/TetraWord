package GraphicEngine;

import javax.swing.JFrame;

public class Window extends JFrame{
  
  public Window(int w, int h){
    JFrame window = new JFrame(); //Create a new window screen
    
    //Window settings
    window.setTitle("TetraWord");
    window.setSize(w, h);
    window.setResizable(false);
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
    window.setVisible(true);
  }
  
}
