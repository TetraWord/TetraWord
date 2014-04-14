
package ContextManager;

import GameEngine.BoardGame;
import GraphicEngine.BrickButton;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GridEventListener extends MouseAdapter implements Observable{
  
  private ArrayList<Observer> listObserver = new ArrayList<>();
  
  public GridEventListener(BoardGame obs){
    addObservateur(obs);
  }
  
  @Override
  public void mouseClicked(MouseEvent e){
    //Get back the BrickButton clicked
    BrickButton b = (BrickButton)e.getSource();
    int x = b.getPosX();
    int y = b.getPosy();
    //System.out.println("b = ("+b.getPosX()+" ; "+b.getPosy()+")");
    //Get back the Brick2D clicked
    int[] coords = new int[2];
    coords[0] = x;
    coords[1] = y;
    updateObservateur(coords);
    
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
}
