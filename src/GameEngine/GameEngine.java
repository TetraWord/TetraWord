package GameEngine;

import ContextManager.ContextManager;
import GameEngine.Dictionnary.Dictionary;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <b> GameEngine is the logical engine of the game.</b>.
 * <p>
 * It's a singleton class, so it can be called everywhere with its <b>
 * getInstance() </b> method. </p>
 * <p>
 * It's the main logical part of the game. It knows all players and contains
 * timer for the game. </p>
 * <p>
 * This class contains:
 * <ul>
 * <li> An instance of the singleton GameEngine. </li>
 * <li> An array of Players in game. </li>
 * <li> The number of Player in game. </li>
 * <li> The Dictionary to check the right word. </li>
 * <li> A boolean to know if an AI is in the game. </li>
 * <li> The time left in Worddle mode for a Player. </li>
 * <li> The game timer. </li>
 * <li> The time until the game stop. </li>
 * </ul>
 * </p>
 *
 * @see Dictionary
 *
 */
public class GameEngine {

  /**
   * Contains the instance of the singleton. Not modifiable.
   *
   * @see GameEngine#getInstance()
   */
  private static final GameEngine INSTANCE = new GameEngine();

  /**
   * The array of Players in game.
   *
   * @see GameEngine#addNewPlayer(GameEngine.Shape, GameEngine.Shape, boolean)
   * @see GameEngine#getPlayers()
   */
  private final Player[] players;

  /**
   * The number of Player in game. Set to 0.
   *
   * @see GameEngine#getNbPlayers()
   */
  private int nbPlayer = 0;

  /**
   * The game Dictionary.
   *
   * @see Dictionary
   */
  private final Dictionary dictionary = new Dictionary();

  /**
   * To know if there is an AI in the game.
   *
   * @see GameEngine#hasAI()
   * @see GameEngine#setAI(boolean)
   */
  private boolean ai = false;

  /**
   * The timer before a end of Player's Worddle mode.
   */
  private Timer worddleTimer = null;

  /**
   * The timer before the game end.
   *
   * @see GameEngine#setGameTimer()
   * @see GameEngine#getTimeLeft()
   */
  private Timer gameTimer = null;

  /**
   * The time when begin the timer. To know how many time left.
   */
  private long tBegin;

  /**
   * The game's time last. Not modifiable. Match for 10 minutes.
   */
  private final long timeToEndGame = 10 * 60 * 1000;

  /**
   * GameEngine constructor. Singleton.
   *
   * Initialize the game for 4 Players max.
   */
  private GameEngine() {
    players = new Player[4];
  }

  /**
   * To get the instance of the singleton GameEngine.
   *
   * @return The instance of the GameEngine
   *
   * @see GameEngine#GameEngine()
   * @see GameEngine#INSTANCE
   */
  public static GameEngine getInstance() {
    return INSTANCE;
  }

  /**
   * Add a new Player to the gameEngine. Create the new Player / AI. Increments
   * the number of Player.
   *
   * @param s The first Shape of the Player
   * @param s2 The second Shape of the Player
   * @param isAI To know if the new Player is an AI oAInot
   * @see Player#Player(int, GameEngine.Shape, GameEngine.Shape,
   * GameEngine.Dictionnary.Dictionary)
   * @see AI#AI(int, GameEngine.Shape, GameEngine.Shape,
   * GameEngine.Dictionnary.Dictionary)
   * @see GameEngine#nbPlayer
   */
  public void addNewPlayer(Shape s, Shape s2, boolean isAI) {
    if (isAI) {
      players[nbPlayer] = new AI(nbPlayer, s, s2, dictionary);
    } else {
      players[nbPlayer] = new Player(nbPlayer, s, s2, dictionary);
    }
    ++nbPlayer;
  }

  /**
   * Get the array of the Players in the game.
   *
   * @return The array of the Players in the game
   * @see GameEngine#players
   */
  public Player[] getPlayers() {
    return players;
  }

  /**
   * Get the number of Players in the game.
   *
   * @return The number of Players in the game
   * @see GameEngine#nbPlayer
   */
  public int getNbPlayers() {
    return nbPlayer;
  }

  /**
   * To know if there is an AI in the game.
   *
   * @return true if there is an AI, else otherwise
   * @see GameEngine#ai
   */
  public boolean hasAI() {
    return ai;
  }

  /**
   * To set the ai variable.
   *
   * @param ai the value of the boolean
   * @see GameEngine#ai
   */
  public void setAI(boolean ai) {
    this.ai = ai;
  }

  /**
   * Start the worddle timer for the Player.
   *
   * @param p The Player in Worddle mode
   */
  public void beginWorddleTimer(Player p) {
    worddleTimer = new Timer();
    worddleTimer.schedule(new WorddleTimerTask((p)), 15000);
  }

  /**
   * Finish the Worddle mode of the Player.
   */
  public void finishTimerWorddle() {
    worddleTimer = null;
  }

  /**
   * To know if a Player is already in Worddle mode.
   *
   * @return true if the Worddle's timer is still alive
   */
  public boolean timerWorddleIsAlive() {
    return worddleTimer != null;
  }

  /**
   * To know if a Player is in a word mode (Worddle / Anagram).
   *
   * @return true if a Player is in a word mode, false otherwise
   */
  public boolean isPlayersInWordMode() {
    for (int i = 0; i < nbPlayer; ++i) {
      if (!players[i].isTetris()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Set the game timer to begin.
   *
   * @see GameEngine#gameTimer
   * @see GameEngine#tBegin
   */
  public void setGameTimer() {
    gameTimer = new Timer();
    tBegin = System.nanoTime();
    gameTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        ContextManager.getInstance().stop();
      }
    }, timeToEndGame);
  }

  /**
   * Get the time last before the game ends.
   *
   * @return the time last before the game ends
   */
  public long getTimeLeft() {
    return ((timeToEndGame * 1000000 - (System.nanoTime() - tBegin)) / 1000000000);
  }

  /**
   * Exchange two Player's Grid.
   * <p>
   * Called by a Modifier.
   * </p>
   *
   * @see Modifier#exchange()
   */
  public void exchange() {
    Player p1 = players[0];
    Player p2 = players[1];

    Grid p1Grid = p1.getGrid();
    Grid p2Grid = p2.getGrid();

    CurrentShape p1CS = p1Grid.getCurrentShape();
    CurrentShape p2CS = p2Grid.getCurrentShape();

    p1Grid.setCurrentShape(p2CS);
    p2Grid.setCurrentShape(p1CS);

    Brick[][] p1TGrid = p1Grid.getTGrid();
    Brick[][] p2TGrid = p2Grid.getTGrid();

    Brick[][] tmp = (Brick[][]) p1TGrid.clone();
    p1Grid.setTGrid(p2TGrid);
    p2Grid.setTGrid(tmp);

    p1Grid.updateObserver(p2CS);
    p2Grid.updateObserver(p1CS);
    p1Grid.updateObserver(p2TGrid);
    p2Grid.updateObserver(p1TGrid);
  }

  /**
   * Stop the game.
   */
  public void stop() {
    for (int i = 0; i < nbPlayer; ++i) {
      players[i].finish();
      players[i].updateObserver("Jeu fini");
      players[i] = null;
    }

    nbPlayer = 0;
    //TO DO
  }

  /**
   * Stop all the timers in the game.
   * <P> Use for the game's pause </p>
   */
  public void stopAllTimers() {
    //TO DO
  }
}
