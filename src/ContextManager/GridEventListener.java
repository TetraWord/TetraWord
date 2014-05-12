package ContextManager;

import GameEngine.Grid;
import GraphicEngine.BrickButton;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * <b> The mouse event listener of the Grid.</b>
 * <p>
 * GridEventListener is an observable object. It notify the Grid when a click is
 * done by the Player on a BrickButton.
 * </p>
 *
 * @see Grid
 */
public final class GridEventListener extends MouseAdapter implements Observable {

  /**
   * The Observer list.
   */
  private ArrayList<Observer> listObserver = new ArrayList<>();

  /**
   * Constructor.
   *
   * @param obs The Grid to Observer.
   */
  public GridEventListener(Grid obs) {
    addObserver(obs);
  }

  /**
   * Get the coordinate of the BrickButton clicked in the Grid and notify it.
   *
   * @param e
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    //Get back the BrickButton clicked
    BrickButton b = (BrickButton) e.getSource();

    //Get his coordonate
    int x = b.getPosX();
    int y = b.getPosY();

    int[] coords = new int[2];
    coords[0] = x;
    coords[1] = y;

    //Notify the Grid that the brick is clicked
    updateObserver(coords);
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
