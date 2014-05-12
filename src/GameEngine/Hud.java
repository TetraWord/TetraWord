package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <b> Hud is used to stock infomation to display to the Player. </b>
 * <p>
 * It's a logical part of the game. </p>
 * <p>
 * Hud means Head-up Display.
 * </p>
 * <p>
 * Each <b> BoardGame </b> in the game has a Hud. </p>.
 * <p>
 * The Hud display the game and the player information. </p>
 * <p>
 * The Hud is observable by the Hud2D, its graphical Part. </p>
 * <p>
 * The Hud is observer of the Player and the BoardGame to display message.
 * </p>
 * <p>
 * The Grid contains:
 * <ul>
 * <li> An ArrayList of his observateur object. </li>
 * <li> A Queue of the message to show. </li>
 * <li> The state of the Game. </li>
 * <li> The level of the Player. </li>
 * <li> The word in progress. </li>
 * <li> The Player's name. </li>
 * <li> The next Shape. </li>
 * <li> The Shape stocked by the Player. </li>
 * <li> The Player's score. </li>
 * <li> The Player's number line removed </li>
 * <li> A boolean to know if the word in progress is finished. </li>
 * <li> The time left before the game's end. </li>
 * <li> The time before the worddle mode. </li>
 * <li> The Modifier stocked by the Player. </li>
 * </ul>
 * </p>
 *
 * @see Player
 * @see BoardGame
 * @see Hud2D
 * @see Shape
 * @see Modifier
 *
 */
public class Hud implements Observer, Observable {

  /**
   * A list of the Observer of the Hud.
   *
   * @see Hud#addObserver(Pattern.Observer)
   * @see Hud#delAllObserver()
   * @see Hud#updateObserver(java.lang.Object)
   */
  private ArrayList<Observer> listObserver = new ArrayList<>();

  /**
   * A list of the message to display.
   *
   * @see Hud#updateObserver(java.lang.Object)
   * @see Hud#getOlderMessage()
   */
  private final Queue<String> listMessage = new LinkedList<>();

  /**
   * The state of the Game (tetris, worddle, anagram, finish).
   *
   * @see Hud#getState()
   */
  private InGameState state;

  /**
   * The Player's level.
   *
   * @see Hud#getLevel()
   */
  private int level;

  /**
   * The Player's name.
   *
   * @see Hud#getPlayerName()
   */
  private String playerName;

  /**
   * The word in progress.
   *
   * @see Hud#getWord() t
   */
  private String word;

  /**
   * The next Shape.
   *
   * @see Hud#getNextShape()
   */
  private Shape nextShape;

  /**
   * The shape stocked by the Player.
   *
   * @see Hud#getStockShape()
   */
  private Shape stockShape;

  /**
   * The Player's score.
   *
   * @see Hud#getScore()
   */
  private int score;

  /**
   * The Player's number removed line.
   *
   * @see Hud#getNbLines()
   */
  private int nbRemovedLine;

  /**
   * A boolean to know if the word in progress is finish or not.
   *
   * @see Hud#isWordFinish()
   */
  private boolean wordFinish;

  /**
   * The time before the game's end.
   *
   * @see Hud#getTimeLeft()
   */
  private long timeLeft;

  /**
   * The time before enable the Worddle mode.
   *
   * @see Hud#getTimeBeforeWorddle()
   */
  private int secondBeforeWorddle;

  /**
   * The Modifier stocked by the Player.
   *
   * @see Hud#getModifier()
   */
  private Modifier modifier;

  /**
   * Hud constructor. Init display's variable.
   */
  public Hud() {
    this.level = 1;
    this.score = 0;
    this.secondBeforeWorddle = 30;
    this.state = InGameState.TETRIS;
    this.nbRemovedLine = 0;
    this.wordFinish = true;
    this.modifier = null;
  }

  /**
   * @return Get the Level
   * @see Hud#level
   */
  public int getLevel() {
    return level;
  }

  /**
   * @return Get the game state
   * @see Hud#state
   */
  public InGameState getState() {
    return state;
  }

  /**
   * @return Get the score
   * @see Hud#score
   */
  public int getScore() {
    return score;
  }

  /**
   * @return Get the word in progress
   * @see Hud#word
   */
  public String getWord() {
    return word;
  }

  /**
   * @return Get the Player's name
   * @see Hud#playerName
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * @return Get the number of lines remove by the Player
   * @see Hud#nbRemovedLine
   */
  public int getNbLines() {
    return nbRemovedLine;
  }

  /**
   * @return Get the time before the game's end
   * @see Hud#timeLeft
   */
  public long getTimeLeft() {
    return timeLeft;
  }

  /**
   * @return Get the time before the Worddle mode for the Player
   * @see Hud#secondBeforeWorddle
   */
  public int getTimeBeforeWorddle() {
    return secondBeforeWorddle;
  }

  /**
   * @return Get the next Shape of the Grid
   * @see Hud#nextShape
   */
  public Shape getNextShape() {
    return nextShape;
  }

  /**
   * @return Get the Shape stocked by the Player
   * @see Hud#stockShape
   */
  public Shape getStockShape() {
    return stockShape;
  }

  /**
   * @return Get the next message to display
   * @see Hud#listMessage
   */
  public String getOlderMessage() {
    return listMessage.poll();
  }

  /**
   * @return Get the Modifier stocked by the Player
   * @see Hud#modifier
   */
  public Modifier getModifier() {
    return modifier;
  }

  /**
   * To know if the word in progress is finish or not.
   *
   * @return True if the word is finish, false otherwise
   * @see Hud#wordFinish
   */
  public boolean isWordFinish() {
    return wordFinish;
  }

  /**
   * Update Hud attributes from Player and BoardGame.
   *
   * @param o Observable whih have notify
   * @param args Arguments
   */
  @Override
  public void update(Observable o, Object args) {
    if (o instanceof Player) {
      Player p = (Player) o;
      this.playerName = p.getName();
      this.stockShape = p.getStockShape();
      this.level = p.getLevel();
      this.score = p.getScore();
      this.state = p.getState();
      this.nbRemovedLine = p.getNbLines();
      this.word = p.getWord();
      double seconds = (double) p.getTime() / 1000000000.0;
      this.secondBeforeWorddle = 30 - (int) Math.round(seconds);
      if (p.isWordFinished()) {
        this.word = null;
      } else {
        this.word = p.getWord();
      }
      this.wordFinish = p.isWordFinished();
      this.modifier = p.getModifier();

      if (args instanceof String) {
        listMessage.add(((String) args));
      } else {
        if (args instanceof int[]) {
          updateObserver((int[]) args);
        }
      }
    }
    if (o instanceof BoardGame) {
      if (args == null) {
        this.nextShape = ((BoardGame) o).getNextShape();
      } else {
        if (args instanceof String) {
          listMessage.add(((String) args));
        }
      }
    }

    timeLeft = GameEngine.getInstance().getTimeLeft();
    updateObserver(null);
  }

  /**
   * @see Observable
   */
  @Override
  public void addObserver(Observer obs) {
    listObserver.add(obs);
  }

  /**
   * @see Observable
   */
  @Override
  public void updateObserver(Object args) {
    for (Observer obs : listObserver) {
      obs.update(this, args);
    }
  }

  /**
   * @see Observable
   */
  @Override
  public void delAllObserver() {
    listObserver = new ArrayList<>();
  }

}
