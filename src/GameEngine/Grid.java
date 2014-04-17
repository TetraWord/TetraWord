package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;

public class Grid implements Observable {

  public static final int sizeX = 10;
  public static final int sizeY = 20;
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

  public void setCurrentShape(CurrentShape cs) {
    currentShape = cs;
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
    for (int i = lineToRemove; i > 0; --i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j] = tGrid[i - 1][j];
      }
    }
    updateObservateur(tGrid);
  }

  public boolean isComplete(CurrentShape s) {
    for (int i = 0; i < Shape.sizeShape; ++i) {
      int x = s.getX() + i;
      if (x >= sizeX) {
        return false;
      }

      if (getTGrid()[0][x].getNb() >= 1) {
        this.myBoardGame.finishGame();
        return true;
      }
      for (int j = 0; j < Shape.sizeShape; ++j) {
        if (s.representation[i][j] >= 1 && getTGrid()[j][x].getNb() >= 1) {
          this.myBoardGame.finishGame();
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
    for (int i = 0; i < Shape.sizeShape; ++i) {
      for (int j = 0; j < Shape.sizeShape; ++j) {
        int y = s.getY() + i;
        int x = s.getX() + j;
        int value = s.representation[i][j];
        if (value > 0 && y < sizeY && x < sizeX) {
          Brick b = s.getComposition()[i][j];
          tGrid[y][x] = new Brick(b);
        }
      }
    }
    updateObservateur(tGrid);
  }

  private void printGrid() {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        System.out.print(tGrid[i][j]);
      }
      System.out.println();
    }
  }

  public Player getPlayer() {
    return myBoardGame.getPlayer();
  }
}
