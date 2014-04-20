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
      case "Nouvelle partie":
        c.defineGameMenu();
        break;

      case "Jouer seul":
        c.definePlayersGame(1);
        break;

      case "Jouer à plusieurs":
        c.defineMultiPlayersMenu();
        break;

      case "Charger partie":
        break;

      case "Contre un joueur":
        c.definePlayersGame(2);
        break;

      case "Contre un IA":
        c.definePlayersGame(1.5);
        break;

      case "Options":
        c.defineOptionGameMenu();
        break;

      case "Préférences":
        c.defineDesignMenu();
        break;

      case "Difficulté":
        c.defineDifficultyMenu();
        break;

      case "Quitter":
        System.exit(0);
        break;

      case "Précédent":
        c.getBack();
        break;

      default:
        System.out.println("No implemented yet");
        break;
    }
  }

}
