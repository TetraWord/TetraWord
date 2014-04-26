package ContextManager;

import GraphicEngine.*;
import GameEngine.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;

/*
 * Chocolate spread
 */
public class ContextManager {

  private final GameEngine gameEngine = GameEngine.getInstance();
  private final GraphicEngine graphicEngine = GraphicEngine.getInstance();
  private static final ContextManager INSTANCE = new ContextManager();
  private static final MenuEventListener menuListener = new MenuEventListener();
  private static final ItemActionListener itemListener = new ItemActionListener();
  private static Config1 player1Listener = null;
  private static Config2 player2Listener = null;
  public boolean isPaused = false;

  private ContextManager() {
  }

  public static ContextManager getInstance() {
    return INSTANCE;
  }

  public KeyAdapter getPlayerListener(int index) {
    if (index == 0) {
      return player1Listener;
    }
    if (index == 1) {
      return player2Listener;
    }
    return null;
  }

  public static MenuEventListener getMenuListener() {
    return menuListener;
  }

  public static ItemActionListener getItemListener() {
    return itemListener;
  }

  public void init() {
    graphicEngine.init();
  }

  public void update() {
    if (isPaused) {
      return;
    }
    graphicEngine.renderFrame();
  }
  
  public void stop() {
    //TO DO
    gameEngine.stop();
    graphicEngine.stop();
  }

  public void defineMainMenu() {
    Window w = graphicEngine.getWindow();
    w.defineMainMenu();
  }

  public void defineGameMenu() {
    Window w = graphicEngine.getWindow();
    w.defineGameMenu();
  }

  public void defineMultiPlayersMenu() {
    Window w = graphicEngine.getWindow();
    w.defineMultiPlayersMenu();
  }

  public void defineOptionGameMenu() {
    Window w = graphicEngine.getWindow();
    w.defineOptionGameMenu();
  }

  public void defineDesignMenu() {
    Window w = graphicEngine.getWindow();
    w.defineDesignMenu();
  }

  public void defineDifficultyMenu() {
    Window w = graphicEngine.getWindow();
    w.defineDifficultyMenu();
  }

  public void reloadApercuBackground(String item) {
    Window w = graphicEngine.getWindow();
    w.reloadApercuBackground(item);
  }

  public void getBack() {
    String lastMenuState = graphicEngine.getWindow().getMenu().getLastState().getStateName();
    switch (lastMenuState) {
      case "Game Menu":
        this.defineGameMenu();
        break;
      case "Main Menu":
        this.defineMainMenu();
        break;
      case "Option Menu":
        this.defineOptionGameMenu();
        break;
      default:
        System.out.println("No implemented yet");
        break;
    }
  }

  public void setPause() {
    isPaused = !isPaused;
  }

  public void definePlayersGame() {
    
    Window w = graphicEngine.getWindow();
    w.clear();
		int numPlayer = gameEngine.getNbPlayers();
		boolean ia = gameEngine.hasIA();

    if (numPlayer > 1) {
      w.setLayout(new GridLayout(1, 2));
    }
    Player[] players = gameEngine.getPlayers();
    for (int i = 0; i < numPlayer; ++i) {
      if (i == 0) {
        player1Listener = new Config1(players[i]);
      }
      if (i == 1 && ia == false) {
        player2Listener = new Config2(players[i]);
      }
			
			w.defineNewBoardGame(players[i].getBoardGame());
			players[i].startTimerBeforeWorddle();

      Thread t = new Thread(new RunPlayer(players[i]));
      t.setDaemon(true);
      t.start();
    }
    
    gameEngine.setGameTimer();
    
    w.addWindowListener();
  }

	void initGame(double numPlayer) {
		/* Define the new settings of the window */
    Window w = graphicEngine.getWindow();
    w.clear();
    /* Add the players with the same shapes */
    Shape s = ShapesStock.getInstance().getRandomShape();
    Shape s2 = ShapesStock.getInstance().getRandomShape();
    for (int i = 0; i < numPlayer; ++i) {
      gameEngine.addNewPlayer(s, s2);
    }

    if (numPlayer > 1) {
      Dimension size = w.getSize();
      size.width *= 2;
      w.setSize(size);
      w.setLayout(new GridLayout(1, 2));
			if(numPlayer == 1.5){
				gameEngine.setIA(true);
			}
    }

    w.setLocationRelativeTo(null);

    Player[] players = gameEngine.getPlayers();
    for (int i = 0; i < numPlayer; ++i) {
			if(numPlayer != 1.5){
				w.defineCommande(i);
			}else{
				if(i == 0){
					w.defineCommande(i);	
				}
			}
    }
	}
}