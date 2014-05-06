package GameEngine;

import GraphicEngine.BoardGame2D;
import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <b> BoardGame is a logical part of the game.</b>
 * <p> Each <b> Player </p> in the game has a BoardGame </p>.
 * <p> The BoardGame is observable by the BoardGame2D, his graphical part.
 * <p> The BoardGame is observer of the hub.
 * <p> The BoardGame contains : 
 *  <ul>
 *  <li> The Player who played on this BoardGame </li>
 *  <li> The logical Grid of the Player </li>
 *  <li> The logical Hub of the Player to display information </li>
 *  <li> The ShapesStock. Singleton. </li>
 *  <li> A Queue with the next Shape of the Player </li>
 *  <li> An ArrayList of his observateur object </li>
 *  <li> An ArrayList of the Brick clicked in the Grid </li>
 *  <li> The coordonates of the last Brick clicked in the Grid </li>
 *  <li> The offsetX and the offsetY of the BoardGame for the shake Modifier </li>
 * </ul>
 * </p>
 * 
 * @see Player
 * @see Grid
 * @see Hub
 * @see BoardGame2D
 * 
 */
public final class BoardGame implements Observable {

  private final Grid grid;
  private final Hub hub;
  private final Player myPlayer;
  private final static ShapesStock ss = ShapesStock.getInstance();
  private final Queue<Shape> listNextShape = new LinkedList<>();
  private ArrayList<Observer> listObserver = new ArrayList<>();
	private int offsetX = 0;
	private int offsetY = 0;

  public BoardGame(int nb, Shape s, Shape s2, Player p) {
    /* Init attributes */
    this.myPlayer = p;
    this.grid = new Grid(this, new CurrentShape(s));
    this.hub = new Hub();
    this.listNextShape.add(s2);

    this.myPlayer.addObservateur(hub);
		addObservateur(hub);
    updateObservateur(null);
  }

  public void finishGame() {
    this.myPlayer.finish();
  }

  public Grid getGrid() {
    return grid;
  }

  public Hub getHub() {
    return hub;
  }

  public Player getPlayer() {
    return myPlayer;
  }

	void setOffset(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}
	
  public Shape getRandomShape() {
    return ss.getRandomShape();
  }

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

  public Shape getNextShape() {
    return listNextShape.element();
  }

  @Override
  public void addObservateur(Observer obs) {
    listObserver.add(obs);
  }

  @Override
  public void updateObservateur(Object args) {
    for (Observer obs : listObserver) {
      obs.update(this, args);
    }
  }

  @Override
  public void delObservateur() {
    listObserver = new ArrayList<>();
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

  public char clickedOneBrick() {
    return grid.clickedOneBrick();
  }

  public void declickedAllBrick() {
    grid.declickedAllBrick();
  }

  StringBuilder getAllLetterFromTheRemovedLine() {
    return grid.getAllLetterFromeTheRemovedLine();
  }

  void removeLine() {
    grid.removeLine(grid.getFirstFullLine());
  }

  void finishWorddle(CurrentShape cs) {
    grid.setAllowDoubleClick(false);
    grid.setCurrentShape(cs);
    grid.destroyAllSelectedBrickInWord();
    grid.declickedAllBrick();
  }

}
