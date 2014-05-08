package ContextManager;

import GraphicEngine.*;
import GameEngine.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;

/**
 * <b> ContextManager is the manager of the game</b>.
 * <p>
 * It's a singleton class, so it can be called everywhere with its <b>
 * getInstance() </b> method. </p>
 * <p>
 * It's the chocolate spread between the graphical part and the logical part of
 * the game </p>
 * <p>
 * This class contains :
 * <ul>
 * <li> An instance of the singleton ContextManager. </li>
 * <li> The GameEngine which is the logical part of the game. Singleton. </li>
 * <li> The GraphicEngine which is the graphical part of the game. Singleton.
 * </li>
 * <li> The MenuEventListener which is the event part of the menu. Not
 * modifiable. </li>
 * <li> The ItemActionListener which is the event part of the design menu. Not
 * modifiable. </li>
 * <li> The Config1 of the player 1. Needed. </li>
 * <li> The Config2 of the player 2. If needed in multiplayer game. </li>
 * <li> A boolean to set the game in pause. </li>
 * </ul>
 * </p>
 *
 * @see GameEngine
 * @see GraphicEngine
 * @see MenuEventListener
 * @see ItemActionListener
 * @see Config1
 * @see Config2
 *
 */
public final class ContextManager {

  /**
   * Contains the instance of the singleton.
   *
   * @see ContextManager#getInstance()
   */
  private static final ContextManager INSTANCE = new ContextManager();

  /**
   * Logical part of the game. Not modifiable. Singleton.
   *
   * @see GameEngine#getInstance()
   */
  private final GameEngine gameEngine = GameEngine.getInstance();

  /**
   * Graphical part of the game. Not modifiable. Singleton.
   *
   * @see GraphicEngine#getInstance()
   */
  private final GraphicEngine graphicEngine = GraphicEngine.getInstance();

  /**
   * Listener of the menu's game. Not modifiable.
   *
   * @see MenuEventListener#MenuEventListener()
   * @see ContextManager#getMenuListener()
   */
  private static final MenuEventListener menuListener = new MenuEventListener();

  /**
   * Listener of the design menu's game. Not modifiable.
   *
   * @see ItemActionListener#ItemActionListener()
   * @see ContextManager#getItemListener()
   */
  private static final ItemActionListener itemListener = new ItemActionListener();

  /**
   * Listener of the player 1's input. Not modifiable.
   *
   * @see ContextManager#getPlayerListener(int)
   * @see ContextManager#definePlayersGame(java.lang.String[])
   */
  private static Config1 player1Listener = null;

  /**
   * Listener of the player 2's input. Not modifiable.
   *
   * @see ContextManager#getPlayerListener(int)
   * @see ContextManager#definePlayersGame(java.lang.String[])
   */
  private static Config2 player2Listener = null;

  /**
   * Know if the game is in paused.
   *
   * @see ContextManager#setPause()
   */
  public boolean isPaused = false;

  /**
   * ContextManager default private constructor. Singleton.
   */
  private ContextManager() {
  }

  /**
   * To get the instance of the singleton ContextManager.
   *
   * @return The instance of the ContextManager.
   *
   * @see ContextManager#ContextManager()
   * @see ContextManager#INSTANCE
   */
  public static ContextManager getInstance() {
    return INSTANCE;
  }

  /**
   * Get a player key event listener.
   *
   * @param index The number of the player
   * @return The KeyEventListener of the player wanted
   *
   * @see Config1
   * @see ContextManager#player1Listener
   * @see ContextManager#player2Listener
   */
  public KeyAdapter getPlayerListener(int index) {
    if (index == 0) {
      return player1Listener;
    }
    if (index == 1) {
      return player2Listener;
    }
    return null;
  }

  /**
   * Get the MenuEventListener of the game's menus.
   *
   * @return MenuEventListener
   *
   * @see MenuEventListener
   * @see ContextManager#menuListener
   */
  public static MenuEventListener getMenuListener() {
    return menuListener;
  }

  /**
   * Get the ItemActionListener of the game's menu design.
   *
   * @return ItemActionListener
   *
   * @see ItemActionListener
   * @see ContextManager#itemListener
   */
  public static ItemActionListener getItemListener() {
    return itemListener;
  }

  /**
   * Switch the Tetris game in pause or in play
   */
  public void setPause() {
    isPaused = !isPaused;
  }

  /**
   * Init the graphicEngine of the game.
   *
   * @see GraphicEngine#init()
   */
  public void init() {
    graphicEngine.init();
  }

  /**
   * Method call by the game loop. Do nothing if the game is in pause. Demand
   * the repaint of the window when call.
   *
   * @see Tetris#run
   * @see GraphicEngine#renderFrame()
   */
  public void update() {
    if (isPaused) {
      return;
    }
    graphicEngine.renderFrame();
  }

  /**
   * Method call when the game is finished.
   *
   * @see GameEngine#stop()
   * @see GraphicEngine#stop()
   */
  public void stop() {
    //TO DO
    gameEngine.stop();
    graphicEngine.stop();
  }

  /**
   * Define the main menu of the tetris.
   *
   * @see Window#defineMainMenu()
   */
  public void defineMainMenu() {
    Window w = graphicEngine.getWindow();
    w.defineMainMenu();
  }

  /**
   * Define the game menu of the tetris.
   *
   * @see Window#defineGameMenu()
   */
  public void defineGameMenu() {
    Window w = graphicEngine.getWindow();
    w.defineGameMenu();
  }

  /**
   * Define the multi-players menu of the tetris.
   *
   * @see Window#defineMultiPlayersMenu()
   */
  public void defineMultiPlayersMenu() {
    Window w = graphicEngine.getWindow();
    w.defineMultiPlayersMenu();
  }

  /**
   * Define the option menu of the tetris.
   *
   * @see Window#defineOptionGameMenu()
   */
  public void defineOptionGameMenu() {
    Window w = graphicEngine.getWindow();
    w.defineOptionGameMenu();
  }

  /**
   * Define the design menu of the tetris.
   *
   * @see Window#defineDesignMenu()
   */
  public void defineDesignMenu() {
    Window w = graphicEngine.getWindow();
    w.defineDesignMenu();
  }

  /**
   * Define the difficulty menu of the tetris.
   *
   * @see Window#defineDifficultyMenu()
   */
  public void defineDifficultyMenu() {
    Window w = graphicEngine.getWindow();
    w.defineDifficultyMenu();
  }

  /**
   * Reload the background displayed in the design menu of the tetris.
   *
   * @param item The name of the background wanted
   *
   * @see Window#reloadApercuBackground(java.lang.String)
   */
  public void reloadApercuBackground(String item) {
    Window w = graphicEngine.getWindow();
    w.reloadApercuBackground(item);
  }

  /**
   * Return to the last menu of the game
   */
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

  /**
   * Define the players game.
   * <p>
   * Create the different <b> BoardGame </b> of the players created in the <b>
   * initGame() </b> method</p>.
   * <p>
   * Create also the needed config of the players </p>
   * <p>
   * When all player's items are create, the player's thread are startes </p>
   * <p>
   * Start all the game timers </p>
   *
   * @param playersName The different players name
   *
   * @see ContextManager#initGame(double)
   * @see Window#defineNewBoardGame(GameEngine.BoardGame)
   * @see Config1#Config1(GameEngine.Player)
   * @see Config2#Config2(GameEngine.Player)
   * @see GameEngine#setGameTimer()
   */
  public void definePlayersGame(String[] playersName) {

    Window w = graphicEngine.getWindow();
    w.clear();
    int numPlayer = gameEngine.getNbPlayers();
    boolean ia = gameEngine.hasAI();

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
      players[i].setName(playersName[i]);

      Thread t;
      if ((i == 1 && ia == false) || i == 0) {
        t = new Thread(new RunPlayer(players[i]));
      } else {
        t = new Thread((AI) players[i]);
      }
      t.setDaemon(true);
      t.start();
    }

    gameEngine.setGameTimer();

    w.addWindowListener();
  }

  /**
   * Init the game with the number of player wanted
   * <p>
   * Create the number of player wanted </p>
   * <p>
   * Create an AI if wanted </p>
   * <p>
   * Add this players to the GameEngine </p>
   * <p>
   * Define the new window size if needed </p>
   * <p>
   * Create the first shape for the game </p>
   *
   * @param numPlayer The number of player wanted :
   * <ul>
   * <li> 1 -> 1 player </li>
   * <li> 1.5 -> 1 player and 1 AI <AIi>
   * <li> 2 -> 2 player </li>
   * </ul>
   *
   * @see ShapesStock
   * @see GameEngine#addNewPlayer(GameEngine.Shape, GameEngine.Shape, boolean)
   */
  void initGame(double numPlayer) {
    /* Define the new settings of the window */
    Window w = graphicEngine.getWindow();
    w.clear();

    if (numPlayer > 1) {
      Dimension size = w.getSize();
      size.width *= 2;
      w.setSize(size);
      w.setLayout(new GridLayout(1, 2));
      if (numPlayer == 1.5) {
        gameEngine.setAI(true);
      }
    }

    /* Add the players with the same shapes */
    Shape s = ShapesStock.getInstance().getRandomShape();
    Shape s2 = ShapesStock.getInstance().getRandomShape();
    for (int i = 0; i < numPlayer; ++i) {
      if (i == 1 && gameEngine.hasAI()) {
        gameEngine.addNewPlayer(s, s2, true);
      } else {
        gameEngine.addNewPlayer(s, s2, false);
      }
    }

    w.setLocationRelativeTo(null);

    Player[] players = gameEngine.getPlayers();
    for (int i = 0; i < numPlayer; ++i) {
      if (numPlayer != 1.5) {
        w.defineCommande(i);
      } else {
        if (i == 0) {
          w.defineCommande(i);
        } else {
          w.defineCommande(2);
        }
      }
    }
  }
}
