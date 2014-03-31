package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;

public class Grid implements Observable {

  private final static ShapesStock ss = ShapesStock.getInstance();
  private final int[][] tGrid = new int[23][13];
  private CurrentShape currentShape;
  private ArrayList<Observer> listObserver = new ArrayList<>();

  public Grid() {
    Shape s = ss.getRandomShape();
    currentShape = new CurrentShape(s);

    for (int i = 0; i < tGrid.length; ++i) {
      for (int j = 0; j < tGrid[i].length; ++j) {
        tGrid[i][j] = -1;
      }
    }
  }

  public int[][] getTGrid() {
    return tGrid;
  }

  public void setTGrid(int[][] tg) {
    for (int i = 0; i < tGrid.length; ++i) {
      for (int j = 0; j < tGrid[i].length; ++j) {
        tGrid[i][j] = tg[i][j];
      }
    }
  }

  public Shape getRandomShape() {
    return ss.getRandomShape();
  }

  public CurrentShape getCurrentShape() {
    return currentShape;
  }

  public void launchNextShape(Shape s) {
    currentShape = new CurrentShape(s);
  }
  
  public int getFirstFullLine(){
    boolean isLineFull = true;
    for(int i = 0; i < 21 ; ++i ){
      for(int j = 0; j < 11; ++j){
        if(tGrid[i][j] < 0){
          isLineFull = false;
        }
      }
      if(isLineFull){
        return i;
      }
    }
    
    return -1;
  }
  
  public void removedFullLines(){
    /* Be carefull because of the number of the line */
    /* Be carefull you need the anagram mode later */
    int numFullLines = 0;
    boolean isLineFull = true;
    for(int i = 19; i > 15; --i ){
      for(int j = 0; j < 11; ++j){
        if(tGrid[i][j] < 0){
          isLineFull = false;
        }
      }
      if(isLineFull){
        ++numFullLines;
      }
      isLineFull = true;
    }
    
    for(int i = 19; i > numFullLines; --i){
      for(int j = 0; j < 11; ++j){
        tGrid[i][j] = tGrid[i-numFullLines][j];
      } 
    }
    
    System.out.println("nombre de full line : "+numFullLines);
  }
  
  public boolean isComplete(){
  	if(getTGrid()[0][3] == 1) {
  		System.out.println("Stop");
  		return true;
  	}
  	return false;
  }

  @Override
  public void addObservateur(Observer obs) {
    listObserver.add(obs);
  }

  @Override
  public void updateObservateur() {
    for (Observer obs : listObserver) {
      obs.update(this, currentShape);
    }
  }

  @Override
  public void delObservateur() {
    listObserver = new ArrayList<>();
  }
}
