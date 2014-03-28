
package ContextManager;

import GameEngine.GameEngine;
import GameEngine.GameState;
import GraphicEngine.Button2D;
import GraphicEngine.GraphicEngine;
import GraphicEngine.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEventListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    GameEngine game = GameEngine.getInstance();
    GraphicEngine g = GraphicEngine.getInstance();
    Button2D click = (Button2D)e.getSource();
    Window w = g.getWindow();
    
    switch(click.getName()){
      case "Start new game":    
        w.defineGameMenu();
        w.repaint();
        break;
      
      case "Single game":
        w.defineNewBoardGame();
        w.repaint();
        game.setState(GameState.IN_GAME);
        game.babarianUpdate();
        break;
        
      case "Multiplayer game":
        
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
