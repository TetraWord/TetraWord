package ContextManager;

import GraphicEngine.Button2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEventListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    ContextManager c = ContextManager.getInstance();
    Button2D click = (Button2D) e.getSource();

    switch (click.getName()) {
      case "Start new game":
        c.defineGameMenu();
        break;

      case "Single game":
        c.definePlayersGame(1);
        break;

      case "Multiplayer game":
        c.defineMultiPlayersMenu();
        break;
      
      case "Load game":
        break;
        
      case "Versus human":
        c.definePlayersGame(2);
        break;
        
      case "Versus IA":
        c.definePlayersGame(1.5);
        break;

      case "Options":
        c.defineOptionGameMenu();
        break;

      case "Design & Theme":
        c.defineDesignMenu();
        break;

      case "Difficulty":
        break;

      case "Exit":
        System.exit(0);
        break;

      case "Previous":
        c.getBack();
        break;

      default:
        System.out.println("No implemented yet");
        break;
    }
    c.update();
  }

}
