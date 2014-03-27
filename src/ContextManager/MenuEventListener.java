
package ContextManager;

import GraphicEngine.Button2D;
import GraphicEngine.GraphicEngine;
import GraphicEngine.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEventListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    GraphicEngine g = GraphicEngine.getInstance();
    Button2D click = (Button2D)e.getSource();

    switch(click.getName()){
      case "Start new game":
        Window w = g.getWindow();
        w.defineNewBoardGame();
        w.repaint();
        break;
        
      case "Load game":
        break;
          
      case "Options":
        break;
         
      case "Exit":
        System.exit(0);
        break;
          
      default:
        System.out.println("No implemented yet");
        break;
    }
  }
  
}
