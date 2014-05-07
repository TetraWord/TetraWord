package GameEngine;

import GraphicEngine.BoardGame2D;
import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <b> BoardGame is a logical part of the game.</b>
 * <p>
 * Each <b> Player </p> in the game has a BoardGame </p>.
 * <p>
 * The BoardGame is observable by the hub. </p>
 * <p>
 * The BoardGame contains :
 * <ul>
 * <li> The Player who played on this BoardGame </li>
 * <li> The logical Grid of the Player </li>
 * <li> The logical Hub of the Player to display information </li>
 * <li> The ShapesStock. Singleton. </li>
 * <li> A Queue with the next Shape of the Player </li>
 * <li> An ArrayList of his observateur object </li>
 * <li> The offsetX and the offsetY of the BoardGame for the shake Modifier
 * </li>
 * </ul>
 * </p>
 *
 * @see Player
 * @see Grid
 * @see Hud
 * @see BoardGame2D
 *
 */
public final class BoardGame implements Observable {

  /**
   * The logical Grid. Not modifiable.
   *
   * @see Grid
   * @see BoardGame#getGrid()
   */
  private final Grid grid;

  /**
   * The logical Hub. Not modifiable.
   *
   * @see Hub
   * @see BoardGame#getHub()
   */
  private final Hud hud;

  /**
   * The player of the BoardGame. Not modifiable.
   *
   * @see Player
   * @see BoardGame#getPlayer()
   */
  private final Player myPlayer;

  /**
   * The singleton ShapeStock. Not modifiable. Provide random Shape.
   *
   * @see ShapesStock
   * @see ShapesStock#getInstance()
   * @see BoardGame#getRandomShape()
   */
  private final static ShapesStock ss = ShapesStock.getInstance();

  /**
   * Queue of next Shape for the Player.
   *
   * @see Shape
   * @see BoardGame#getNextShape()
   */
  private final Queue<Shape> listNextShape = new LinkedList<>();

  /**
   * List of observateur.
   */
  private ArrayList<Observer> listObserver = new ArrayList<>();

  /**
   * offset on axe x for the Modifier "shake"
   */
  private int offsetX = 0;

  /**
   * offset on axe y for the Modifier "shake"
   */
  private int offsetY = 0;

  /**
   * Constructor BoardGame.
   * <p>
   * Initialize the Player of the BoardGame, create a Grid and a Hub for the
   * BoardGame. Add a Shape to the list of the next Shape for the Player. Add
   * the new Hub as on Overser of Player and BoardGame for the futur message
   * displayed during the game.
   * </p>
   *
   * @param s The first Shape of the Game
   * @param s2 The Second Shape of the Game
   * @param p The Player associate at the BoardGame
   *
   * @see Grid#Grid(GameEngine.BoardGame, GameEngine.CurrentShape)
   */
  public BoardGame(Shape s, Shape s2, Player p) {
    /* Init attributes */
    this.myPlayer = p;
    this.grid = new Grid(this, new CurrentShape(s));
    this.hud = new Hud();
    this.listNextShape.add(s2);

    this.myPlayer.addObservateur(hud);
    addObservateur(hud);
    updateObservateur(null);
  }

  /**
   * Get the BoardGame's Grid
   *
   * @return The Grid in the BoardGame
   *
   * @see Grid
   */
  public Grid getGrid() {
    return grid;
  }
  /**
   * Get the BoardGame's Hub
   *
   * @return The Hub in the BoardGame
   *
   * @see Hub
   */
  public Hud getHub() {
    return hud;
  }

  /**
   * Get the BoardGame's Player
   *
   * @return The BoardGame's Player
   *
   * @see Player
   */  
  public Player getPlayer() {
    return myPlayer;
  }

  /**
   * Get the offsetX.
   * 
   * @return The offsetX
   * 
   * @see BoardGame#offsetX
   */
  public int getOffsetX() {
    return offsetX;
  }

  /**
   * Get the offsetY.
   * 
   * @return The offsetY
   * 
   * @see BoardGame#offsetY
   */
  public int getOffsetY() {
    return offsetY;
  }
  
  /**
   * Set new offset x and new offset y.
   * 
   * @param offsetX
   * @param offsetY
   * 
   * @see BoardGame#offsetX
   * @see BoardGame#offsetY
   */
  void setOffset(int offsetX, int offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }
  
  /**
   * Get a random Shape from the ShapeStock.
   * 
   * @return The random Shape create by the ShapeStock
   * 
   * @see ShapesStock#getRandomShape() 
   */
  public Shape getRandomShape() {
    return ss.getRandomShape();
  }

  /**
   * Get the next Shape in the Queue.
   * 
   * @return The Shape get in the Queue
   * 
   * @see BoardGame#listNextShape
   */
  public Shape getNextShape() {
    return listNextShape.element();
  }

  /**
   * Launch the next Shape.
   * Create a new CurrentShape from the first Shape of the Queue.
   * Add a new random Shape in the Players's next Shape Queue.
   * Set the new CurrentShape in the Grid.
   * Update
   * 
   * @see CurrentShape
   * @see Grid
   */
  public void launchNextShape() {
    int size = listNextShape.size();
    CurrentShape cs = new CurrentShape(listNextShape.poll());
    Shape s = new CurrentShape(getRandomShape());

    Player[] tabP = GameEngine.getInstance().getPlayers();
    for (int i = 0; i < GameEngine.getInstance().getNbPlayers(); ++i) {
      tabP[i].getBoardGame().listNextShape.add(s);
    }

    myPlayer.canSwitchShape(true);

    updateObservateur(null);

    if (!grid.isComplete(cs)) {
      grid.setCurrentShape(cs);
    }
    grid.updateObservateur(cs);
    grid.updateObservateur(grid.getTGrid());

  }

  /**
   * @see Observable 
   */
  @Override
  public void addObservateur(Observer obs) {
    listObserver.add(obs);
  }
  
  /**
   * @see Observable 
   */
  @Override
  public void updateObservateur(Object args) {
    for (Observer obs : listObserver) {
      obs.update(this, args);
    }
  }
  
  /**
   * @see Observable 
   */
  @Override
  public void delObservateur() {
    listObserver = new ArrayList<>();
  }

  public void finishGame() {
    this.myPlayer.finish();
  }

  public void finishFall(CurrentShape s) {
    grid.setIn(s);
    if (grid.getFirstFullLine() != -1) {
      grid.setCurrentShape(null);
      myPlayer.switchToAnagram(true);
    } else {
      launchNextShape();
    }
  }

  void finishWorddle(CurrentShape cs) {
    grid.setAllowDoubleClick(false);
    grid.setCurrentShape(cs);
    grid.destroyAllSelectedBrickInWord();
    grid.declickedAllBrick();
  }

}
