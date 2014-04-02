package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;

public class Grid implements Observable {

  private final int[][] tGrid = new int[23][13];
  private CurrentShape currentShape;
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final BoardGame myBoardGame;

  public Grid(BoardGame b, CurrentShape cS) {
    this.myBoardGame = b;
    this.currentShape = cS;
    for (int i = 0; i < tGrid.length; ++i) {
      for (int j = 0; j < tGrid[i].length; ++j) {
        tGrid[i][j] = -1;
      }
    }
  }

  public int[][] getTGrid() {
    return tGrid;
  }

  public CurrentShape getCurrentShape() {
    return currentShape;
  }

  public int getFirstFullLine() {
    boolean isLineFull = true;
    for (int i = 0; i < 21; ++i) {
      for (int j = 0; j < 11; ++j) {
        if (tGrid[i][j] < 0) {
          isLineFull = false;
        }
      }
      if (isLineFull) {
        return i;
      }
    }

    return -1;
  }

  public void removedFullLines() {
    /* Be carefull because of the number of the line */
    /* Be carefull you need the anagram mode later */
    int numFullLines = 0;
    boolean isLineFull = true;
    for (int i = 19; i > 15; --i) {
      for (int j = 0; j < 11; ++j) {
        if (tGrid[i][j] < 0) {
          isLineFull = false;
        }
      }
      if (isLineFull) {
        ++numFullLines;
      }
      isLineFull = true;
    }

    for (int i = 19; i > numFullLines; --i) {
      for (int j = 0; j < 11; ++j) {
        tGrid[i][j] = tGrid[i - numFullLines][j];
      }
    }

    System.out.println("nombre de full line : " + numFullLines);
  }

  public boolean isComplete() {
  	for(int i=0; i<4; ++i){
	    if (getTGrid()[0][currentShape.getX() + i] == 1) {
	      System.out.println("Stop");
	      return true;
	    }
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

  void setIn(CurrentShape s) {
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        int value = s.representation[i][j];
        if (value > 0) {
          tGrid[s.getY() + i][s.getX() + j] = s.representation[i][j];
        }
      }
    }

    removedFullLines();
    if (!isComplete()) {
      currentShape = myBoardGame.launchNextShape();
    }
    updateObservateur();
  }

  private void printGrid() {
    for (int i = 0; i < 20; ++i) {
      for (int j = 0; j < 10; ++j) {
        System.out.print(tGrid[i][j]);
      }
      System.out.println();
    }
  }
}
