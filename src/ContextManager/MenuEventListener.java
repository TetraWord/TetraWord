
package ContextManager;

import GraphicEngine.Button2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEventListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    ContextManager c = ContextManager.getInstance();
    Button2D click = (Button2D)e.getSource();

    switch(click.getName()){
      case "Start new game":    
        c.defineGameMenu();
        break;
      
      case "Single game":
        c.definePlayersGame(1);
        break;
        
      case "Multiplayer game":
        c.definePlayersGame(2);
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
