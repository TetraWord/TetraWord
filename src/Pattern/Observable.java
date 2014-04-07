
package Pattern;

public interface Observable {
  public void addObservateur(Observer obs);
  public void updateObservateur(Object args);
  public void delObservateur();
}
