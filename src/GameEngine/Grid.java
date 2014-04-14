package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;

public class Grid implements Observable {

  private static final int sizeX = 10;
  private static final int sizeY = 20;
  private final Brick[][] tGrid = new Brick[sizeY][sizeX];
  private CurrentShape currentShape;
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final BoardGame myBoardGame;

  public Grid(BoardGame b, CurrentShape cS) {
    this.myBoardGame = b;
    this.currentShape = cS;
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j] = new Brick(' ', -1);
      }
    }
  }

  public Brick[][] getTGrid() {
    return tGrid;
  }

  public CurrentShape getCurrentShape() {
    return currentShape;
  }

  public int getFirstFullLine() {
    boolean isLineFull = true;
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        if (tGrid[i][j].getNb() < 0) {
          isLineFull = false;
        }
      }
      if (isLineFull) {
        return i;
      }
      isLineFull = true;
    }

    return -1;
  }

  public void removeLine(int lineToRemove) {
    /* Be carefull because of the number of the line */
    /* Be carefull you need the anagram mode later */

    for (int i = lineToRemove; i > 0; --i) {
      for (int j = 0; j < 10; ++j) {
        tGrid[i][j] = tGrid[i - 1][j];
        updateObservateur(tGrid);
      }
    }
  }

  public boolean isComplete(CurrentShape s) {
    for (int i = 0; i < 4; ++i) {
      int x = currentShape.getX() + i;
      if (x >= 10) {
        return false;
      }

      if (getTGrid()[0][currentShape.getX() + i].getNb() >= 1) {
        //System.out.println("Stop");
        this.myBoardGame.setPlay();
        return true;
      }
      for (int j = 0; j < 4; ++j) {
        if (s.representation[i][j] >= 1 && getTGrid()[j][currentShape.getX() + i].getNb() >= 1) {
          //System.out.println("Stop");
          this.myBoardGame.setPlay();
          return true;
        }
      }
    }

    return false;
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

  void setIn(CurrentShape s) {

    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        int y = s.getY() + i;
        int x = s.getX() + j;
        int value = s.representation[i][j];
        if (value > 0 && y < sizeY && x < sizeX) {
          Brick b = s.getComposition()[i][j];
          tGrid[y][x] = new Brick(b);
        }
      }
    }

    int line = getFirstFullLine();
    
    while (line != -1) {
      
      removeLine(line);
      
      line = getFirstFullLine();

      /* Remove this later it's just for some test 
      myBoardGame.getPlayer().setLevelUp();
      int level = myBoardGame.getPlayer().getLevel();
      myBoardGame.updateObservateur(level); */

    }

    CurrentShape cs = myBoardGame.launchNextShape();
    if (!isComplete(cs)) {
      currentShape = cs;
    }
    updateObservateur(currentShape);
  }

  private void printGrid() {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        System.out.print(tGrid[i][j]);
      }
      System.out.println();
    }
  }
}
