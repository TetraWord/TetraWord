package Pattern;

public interface Observable {

  public void addObserver(Observer obs);

  public void updateObserver(Object args);

  public void delAllObserver();
}
