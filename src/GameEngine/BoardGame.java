package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;

public class BoardGame implements Observable {
	private final Grid grid;
  private final int nb;
  private ArrayList<Observer> listObserver = new ArrayList<>();
  
  public BoardGame(int nb){
    this.nb = nb;
    grid = new Grid();
  }
  
  public Grid getGrid(){
    return grid;
  }
  
  public int getNb(){
    return nb;
  }

  @Override
  public void addObservateur(Observer obs) {
    listObserver.add(obs);
  }

  @Override
  public void updateObservateur() {
    for(Observer obs : listObserver){
      obs.update(this, null);
    }
  }

  @Override
  public void delObservateur() {
    listObserver = new ArrayList<>();
  }

  void setGridUpdate() {
    grid.updateObservateur();
  }

}
