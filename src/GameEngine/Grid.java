package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.Random;

public class Grid implements Observable, Observer {

  public static final int sizeX = 10;
  public static final int sizeY = 20;
  private Brick[][] tGrid = new Brick[sizeY][sizeX];
  private CurrentShape currentShape;
  private CurrentModifier currentModifier;
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final BoardGame myBoardGame;
  private boolean allowClick = false;
  private boolean allowDoubleClicked = false;
  private final ArrayList<Brick> tabBrickClicked = new ArrayList<>();
  private int[] coordsLastBrickClicked = new int[2];

  public Grid(BoardGame b, CurrentShape cS) {
    this.myBoardGame = b;
    this.currentShape = cS;
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j] = new Brick(' ', -1);
      }
    }
  }

  Grid(Grid g) {
    this(g.myBoardGame, g.getCurrentShape());
    this.tGrid = new Brick[sizeY][sizeX];
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j] = new Brick(g.tGrid[i][j]);
      }
    }
  }

  public Brick[][] getTGrid() {
    return tGrid;
  }

  public void setTGrid(Brick[][] newTGrid) {
    tGrid = (Brick[][]) newTGrid.clone();
  }

  public CurrentShape getCurrentShape() {
    return currentShape;
  }

  public void setCurrentShape(CurrentShape cs) {
    currentShape = cs;
    updateObservateur(cs);
  }
  
  public CurrentModifier getCurrentModifier() {
    return currentModifier;
  }

  public void setCurrentModifier(CurrentModifier cm) {
    currentModifier = cm;
    updateObservateur(cm);
  }
  
  public void setAllowClick(boolean b) {
    allowClick = b;
  }

  public boolean isAllowClick() {
    return allowClick;
  }
  
  public void setAllowDoubleClick(boolean b) {
    allowDoubleClicked = b;
  }
  
  public boolean isAllowDoubleClick() {
    return allowDoubleClicked;
  }
  
  @Override
  public void update(Observable o, Object args) {
    if (args instanceof int[] && isAllowClick()) {
      /*We select the clicked Brick */
      int x = ((int[]) args)[0];
      int y = ((int[]) args)[1];
      int lastX = x;
      int lastY = y;

      if (coordsLastBrickClicked != null) {
        lastX = coordsLastBrickClicked[0];
        lastY = coordsLastBrickClicked[1];
      }

      Brick b = tGrid[y][x];

      /*If the brick is on the full line and if it's not clicked yet */
      Player p = myBoardGame.getPlayer();
      
      if (p.isAnagram()) {
        if (y == getFirstFullLine() && !b.isClicked()) {
          p.addNewChar(b.getLetter());
          b.setClicked(true);
        }
      } else if (p.isWorddle()) {
        if (!b.isClicked() && !isAllowDoubleClick() && Math.abs(lastX - x) < 2 && Math.abs(lastY - y) < 2) {
          b.setClicked(true);
          tabBrickClicked.add(b);
          p.addNewChar(b.getLetter());
          coordsLastBrickClicked = getBrickCoordInGrid(b);
        } else if (isAllowDoubleClick() && !b.isDoubleClicked() && b.isClicked() && Math.abs(lastX - x) < 2 && Math.abs(lastY - y) < 2) {
          doubleClickedAllBrickClicked(b);
          setAllowDoubleClick(false);
          p.addNewChar(b.getLetter());
          coordsLastBrickClicked = getBrickCoordInGrid(b);
        }
      }
    }
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

  public int getNbFullLine() {
    boolean isLineFull = true;
    int nbLineFull = 0;
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        if (tGrid[i][j].getNb() < 0) {
          isLineFull = false;
        }
      }
      if (isLineFull) {
        ++nbLineFull;
      }
      isLineFull = true;
    }

    return nbLineFull;
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

      if (tGrid[0][x].getNb() >= 1) {
        this.myBoardGame.finishGame();
        return true;
      }
      for (int j = 0; j < Shape.sizeShape; ++j) {
        if (s.representation[i][j] >= 1 && tGrid[j][x].getNb() >= 1) {
          this.myBoardGame.finishGame();
          return true;
        }
      }
    }

    return false;
  }
  
  void finishWorddle(CurrentShape cs) {
    setAllowDoubleClick(false);
    setCurrentShape(cs);
    destroyAllSelectedBrickInWord();
    declickedAllBrick();
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
        if (value > 0 && y < sizeY && y > 0 && x < sizeX) {
          Brick b = s.getComposition()[i][j];
          tGrid[y][x] = new Brick(b);
        }
      }
    }
    updateObservateur(tGrid);
  }

  public void printGrid() {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        System.out.print(tGrid[i][j].getNb());
      }
      System.out.println();
    }

    System.out.println();
    System.out.println();
  }

  public Player getPlayer() {
    return myBoardGame.getPlayer();
  }

  public char clickedOneBrick() {
    Random r = new Random();

    int x, y;
    do {
      x = (int) (Math.random() * sizeX);
      y = (int) (Math.random() * sizeY);
    } while (tGrid[y][x].getNb() < 0);
    Brick b = tGrid[y][x];
    b.setClicked(true);
    
    tabBrickClicked.add(b);
    coordsLastBrickClicked = getBrickCoordInGrid(b);
    return b.getLetter();
  }
  
  public void setNoLastBrickClicked() {
    coordsLastBrickClicked = null;
  }

  public void setBricksToDestroy() {
    for (int i = 0; i < tabBrickClicked.size(); ++i) {
      tabBrickClicked.get(i).setToDestroy();
    }
    clearTabBrickClicked();
  }
  
  public void clearTabBrickClicked() {
    tabBrickClicked.clear();
  }

  public void declickedAllBrick() {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j].setClicked(false);
      }
    }
  }

  public void destroyAllSelectedBrickInWord() {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        if (tGrid[i][j].isClicked() && tGrid[i][j].gonnaBeDestroyed()) {
          doGravityOnBrick(i, j);
        }
      }
    }
    updateObservateur(tGrid);
  }

  public void doubleClickedAllBrickClicked(Brick b) {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        if (tGrid[i][j].isClicked() && tGrid[i][j] != b) {
          tGrid[i][j].setClicked(true);
        }
      }
    }
  }

  public int[] getBrickCoordInGrid(Brick b) {
    int[] coords = new int[2];
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        if (tGrid[i][j] == b) {
          coords[0] = j;
          coords[1] = i;
          return coords;
        }
      }
    }
    return null;
  }

  public void doGravityOnBrick(int y, int x) {
    while (y > 1) {
      tGrid[y][x] = new Brick(tGrid[y - 1][x]);
      --y;
    }
  }

  public int[] DetermineAccessibleTranslationsForPieceOrientation(CurrentShape shape, int moveIsPossible, int minDeltaX, int maxDeltaX) {
    // Clear Results.
    moveIsPossible = 0;
    minDeltaX = 0;
    maxDeltaX = 0;

    int width = sizeX;
    int height = sizeY;

    int[] result = new int[3];
    result[0] = moveIsPossible;
    result[1] = minDeltaX;
    result[2] = maxDeltaX;

    CurrentShape tmp_Shape;
    int moveAcceptable = 0;
    int trialTranslationDelta = 0;

    // Check if we can move at all.
    moveAcceptable = IsGoalAcceptable(shape);
    if (0 != moveAcceptable) {
      moveIsPossible = 1;
    } else {
      return result;
    }

    // Scan from center to left to find left limit.
    int stillAcceptable = 1;
    for (trialTranslationDelta = shape.getX(); trialTranslationDelta >= 0; trialTranslationDelta--) {

      if (stillAcceptable == 0) {
        break;
      }
      // Copy piece to temp and translate
      tmp_Shape = new CurrentShape(shape);
      tmp_Shape.move(trialTranslationDelta, 0);

      moveAcceptable = IsGoalAcceptable(tmp_Shape);
      if (0 != moveAcceptable) {
        minDeltaX = trialTranslationDelta;
      } else {
        stillAcceptable = 0;
      }
    }

    // Scan from center to right to find right limit.
    stillAcceptable = 1;
    for (trialTranslationDelta = shape.getX(); trialTranslationDelta < width; trialTranslationDelta++) {
      if (stillAcceptable == 0) {
        break;
      }
      // Copy piece to temp and translate
      tmp_Shape = new CurrentShape(shape);
      tmp_Shape.move(trialTranslationDelta, 0);

      moveAcceptable = IsGoalAcceptable(tmp_Shape);
      if (0 != moveAcceptable) {
        maxDeltaX = trialTranslationDelta;
      } else {
        stillAcceptable = 0;
      }
    }

    result[0] = moveIsPossible;
    result[1] = minDeltaX;
    result[2] = maxDeltaX;

    return result;
  }

  public int IsGoalAcceptable(CurrentShape shape) {
    // Fast check: If piece origin lies outside board, goal is not acceptable.
    if (shape.getX() < 0) {
      return (0);
    }

    if (shape.getY() < 0) {
      return (0);
    }

    if (shape.getX() >= sizeX) {
      return (0);
    }

    if (shape.getY() >= sizeY) {
      return (0);
    }

    // Consider the absolute position of all points of the piece.
    // If any of the points lie outside the board, goal is not acceptable.
    // If any of the points coincide with an occupied cell of the board,
    //  the goal is not acceptable.
    for (int y = 0; y <= shape.getMaxHeight(shape.getRepresentation()); ++y) {
      for (int x = 0; x <= shape.getMaxWidth(shape.getRepresentation()); ++x) {
        // Absolute point must be in the board area        
        int absoluteX = shape.getX() + x;
        int absoluteY = shape.getY() + y;

        if (absoluteX < 0) {
          return (0);
        }

        if (absoluteX >= sizeX) {
          return (0);
        }

        if (absoluteY < 0) {
          return (0);
        }

        if (absoluteY >= sizeY) {
          return (0);
        }

        // Absolute point cannot overlap an occupied cell of the board.
        if (tGrid[y][x].getNb() > 0) {
          return (0);
        }
      }
    }

    // If we made it to this point, the goal is acceptable.
    return (1);
  }

  public void FullDropAndAddPieceToBoard(CurrentShape tmpShape) {
    int finalLine = tmpShape.getY();
    while (!tmpShape.tryCollision(getTGrid(), tmpShape.getX(), finalLine)) {
      ++finalLine;
    }
    tmpShape.move(tmpShape.getX(), finalLine - 1);
    setIn(tmpShape);
    //printGrid();
  }

  public int getHeightColAt(int x) {
    for (int y = 0; y < sizeY; ++y) {
      if (tGrid[y][x].getNb() > 0) {
        return sizeY - y;
      }
    }

    return 0;
  }

  public int PileHeightWeightedCells() {
    int maxY = 0;
    int tmpY;

    for (int x = 0; x < sizeX; ++x) {
      for (int y = 0; y < sizeY; ++y) {
        if (tGrid[y][x].getNb() > 0) {
          tmpY = 20 - y;
          if (maxY < tmpY) {
            maxY = tmpY;
          }
          y = sizeY;
        }
      }
    }

    return maxY;
  }

  public int SumOfWellHeights() {
    int totalWeightedCells = 0;

    for (int x = 0; x < sizeX; ++x) {
      totalWeightedCells += getHeightColAt(x);
    }
    
    return totalWeightedCells;
  }

  public StringBuilder getAllLetterFromTheRemovedLine() {
    StringBuilder sb = new StringBuilder();
    int line = getFirstFullLine();
    for (int j = 0; j < sizeX; ++j) {
      sb.append(getTGrid()[line][j].getLetter());
    }
    return sb;
  }

  void selectBrick(int y, char charAt) {
    for (int i = 0; i < sizeX; ++i) {
      if (tGrid[y][i].getLetter() == charAt) {
        tGrid[y][i].setClicked(true);
        return;
      }
    }
  }

}
