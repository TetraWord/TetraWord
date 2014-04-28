package ContextManager;

import GameEngine.GameEngine;
import GraphicEngine.Button2D;
import GraphicEngine.GraphicEngine;
import GraphicEngine.OverBoard2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
				c.initGame(1);
        break;

      case "Jouer à plusieurs":
        c.defineMultiPlayersMenu();
        break;

      case "Charger partie":
        break;

      case "Contre un joueur":
				c.initGame(2);
        break;

      case "Contre un IA":
				c.initGame(1.5);
        break;

      case "Jouer":
        ArrayList<OverBoard2D> tab = GraphicEngine.getInstance().getWindow().getOverBoards();
        String[] playersName;
        
        if(GameEngine.getInstance().hasIA()){
          playersName = new String[tab.size() + 1];
          playersName[tab.size()-1] = "IA";
					for(int i = 0; i < tab.size() - 1; ++i){
						playersName[i] = tab.get(i).getPlayerName();
					}
        } else {  
          playersName = new String[tab.size()];
					for(int i = 0; i < tab.size(); ++i){
						playersName[i] = tab.get(tab.size()).getPlayerName();
					}
        }
        c.definePlayersGame(playersName);
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
