package Pattern;

/**
 * <b>Interface for Observable objects. </b>
 */
public interface Observable {

  /**
   * Add an observer to the set of observers.
   *
   * @param obs The new Observer
   */
  public void addObserver(Observer obs);

  /**
   * Update all Observer.
   *
   * @param args Arguments
   */
  public void updateObserver(Object args);

  /**
   * Delete all Observer.
   */
  public void delAllObserver();
}
