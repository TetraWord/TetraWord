package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <b> Grid is a logical part of the game.</b>
 * <p>
 * Each <b> BoardGame </b> in the game has a Grid </p>.
 * <p>
 * The Grid is observable by the Grid2D, its graphical Part. </p>
 * <p>
 * The Grid is observer of the GridEventListener.
 * </p>
 * <p>
 * The Grid contains :
 * <ul>
 * <li> A horizontal size </li>
 * <li> A vertical size </li>
 * <li> A Brick composition </li>
 * <li> A CurrentShape </li>
 * <li> A CurrentModifier </li>
 * <li> An ArrayList of his observateur object </li>
 * <li> The offsetX and the offsetY of the BoardGame for the shake Modifier
 * </li>
 * <li> The BoardGame who contains the Grid </li>
 * <li> A boolean to know if we can click in the Grid </li>
 * <li> A boolean to know if we can double click in the Grid </li>
 * <li> A list of the Brick clicked in the Grid </li>
 * <li> The coordinates of the last Brick clicked </li>
 * </ul>
 * </p>
 *
 * @see Brick
 * @see CurrentShape
 * @see CurrentModifier
 * @see BoardGame
 *
 */
public class Grid implements Observable, Observer {

  /**
   * The horizontal size of the Grid. Not modifiable.
   */
  public static final int sizeX = 10;

  /**
   * The vertical size of the Grid. Not modifiable.
   */
  public static final int sizeY = 20;

  /**
   * The Brick table composition of the Grid.
   *
   * @see Grid#getTGrid()
   * @see Grid#setTGrid(GameEngine.Brick[][])
   */
  private Brick[][] tGrid = new Brick[sizeY][sizeX];

  /**
   * The CurrentShape who fall in the Grid.
   *
   * @see Grid#getCurrentShape()
   * @see Grid#setCurrentShape(GameEngine.CurrentShape)
   */
  private CurrentShape currentShape;

  /**
   * The CurrentModifier who is in the Grid.
   *
   * @see Grid#getCurrentModifier()
   * @see Grid#setCurrentModifier(GameEngine.CurrentModifier)
   */
  private CurrentModifier currentModifier;

  /**
   * The Observer of the Grid.
   *
   * @see Grid#addObserver(Pattern.Observer)
   * @see Grid#updateObserver(java.lang.Object)
   * @see Grid#delAllObserver()
   */
  private ArrayList<Observer> listObserver = new ArrayList<>();

  /**
   * The BoardGame who contains the Grid. Not modifiable.
   */
  private final BoardGame myBoardGame;

  /**
   * To know if the Player can click a Brick in the Grid.
   *
   * @see Grid#setAllowClick(boolean)
   * @see Grid#isAllowClick()
   */
  private boolean allowClick = false;

  /**
   * To know if a Brick can be double clicked in the Grid.
   *
   * @see Grid#setAllowDoubleClick(boolean)
   * @see Grid#isAllowDoubleClick()
   */
  private boolean allowDoubleClicked = false;

  /**
   * A list of the Brick clicked in the Grid.
   *
   * @see Grid#selectBrick(int, char)
   */
  private final ArrayList<Brick> tabBrickClicked = new ArrayList<>();

  /**
   * The coordinates of the last Brick clicked by the Player.
   */
  private int[] coordsLastBrickClicked = new int[2];

  /**
   * The Timer in charge of the CurrentModifier's Display
   */
  private Timer timerBeforeModifier = null;

  /**
   * Grid constructor. Init its BoardGame and its first CurrentShape. Init the
   * composition of Brick to null Brick.
   *
   * @param b The BoardGame who contains the Grid.
   * @param cS The first CurrentShape of the Grid.
   */
  public Grid(BoardGame b, CurrentShape cS) {
    this.myBoardGame = b;
    this.currentShape = cS;
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j] = new Brick(' ', -1);
      }
    }
    displayModifier();
  }

  /**
   * Copy constructor.
   *
   * @param g The Grid to be copy.
   */
  Grid(Grid g) {
    this(g.myBoardGame, g.getCurrentShape());
    this.tGrid = new Brick[sizeY][sizeX];
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j] = new Brick(g.tGrid[i][j]);
      }
    }
  }

  /**
   * Get the Grid's Brick composition.
   *
   * @return The Grid's Brick composition.
   * @see Grid#tGrid
   */
  public Brick[][] getTGrid() {
    return tGrid;
  }

  /**
   * Set a new Brick composition to the Grid.
   *
   * @param newTGrid The new Brick composition.
   * @see Grid#tGrid
   */
  public void setTGrid(Brick[][] newTGrid) {
    tGrid = (Brick[][]) newTGrid.clone();
  }

  /**
   * Get the CurrentShape falling in the Grid.
   *
   * @return The CurrentShape falling in the Grid.
   * @see Grid#currentShape
   */
  public CurrentShape getCurrentShape() {
    return currentShape;
  }

  /**
   * Set a new CurrentShape in the Grid and update the Grid2D.
   *
   * @param cs The new CurrentShape
   * @see Grid#currentShape
   */
  public void setCurrentShape(CurrentShape cs) {
    currentShape = cs;
    updateObserver(cs);
  }

  /**
   * Get the CurrentModifier who is in the Grid.
   *
   * @return The CurrentModifier in the Grid.
   * @see Grid#currentModifier
   */
  public CurrentModifier getCurrentModifier() {
    return currentModifier;
  }

  /**
   * Set a CurrentModifier in the Grid and update the Grid2D.
   *
   * @param cm The CurrentModifier set in the Grid.
   * @see Grid#currentModifier
   */
  public void setCurrentModifier(CurrentModifier cm) {
    currentModifier = cm;
    updateObserver(cm);
  }

  /**
   * Set the Grid clickable or not.
   *
   * @param b True if Grid is clickable, false otherwise.
   * @see Grid#allowClick
   */
  public void setAllowClick(boolean b) {
    allowClick = b;
  }

  /**
   * @return True if Grid is clickable, false otherwise.
   * @see Grid#allowClick
   */
  public boolean isAllowClick() {
    return allowClick;
  }

  /**
   * Set the Grid double clickable or not.
   *
   * @param b True if Grid is double clickable, false otherwise.
   * @see Grid#allowDoubleClicked
   */
  public void setAllowDoubleClick(boolean b) {
    allowDoubleClicked = b;
  }

  /**
   * @return True if Grid is double clickable, false otherwise.
   * @see Grid#allowDoubleClicked
   */
  public boolean isAllowDoubleClick() {
    return allowDoubleClicked;
  }

  /**
   * Get the Player who played with the Grid.
   *
   * @return The Player who played with the Grid.
   */
  public Player getPlayer() {
    return myBoardGame.getPlayer();
  }

  /**
   * Get the first full line of the Grid.
   *
   * @return The position of the first full line or -1 if no line is full.
   */
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

  /**
   * Get the number of full line in the Grid.
   *
   * @return the number of full line.
   */
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

  /**
   * Remove a line in the Grid and update the Grid2D.
   *
   * @param lineToRemove The line to remove in the Grid.
   */
  public void removeLine(int lineToRemove) {
    for (int i = lineToRemove; i > 0; --i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j] = tGrid[i - 1][j];
      }
    }
    updateObserver(tGrid);
  }

  /**
   * Test if the Grid can contain the CurrentShape without being outside.
   *
   * @param s The CurrentShape tested
   * @return True if the Grid can't include the CurrentShape, false otherwise.
   */
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

  /**
   * Finish the Worddle mode. Called from the Player. Disable the double click,
   * reput the previous CurrentShape in the Grid, destroy all brick selected in
   * a word and declick all other brick clicked.
   *
   * @param cs The CurrentShape to be in the Grid for falling.
   */
  public void finishWorddle(CurrentShape cs) {
    setAllowDoubleClick(false);
    setCurrentShape(cs);
    destroyAllSelectedBrickInWord();
    declickedAllBrick();
  }

  /**
   * Set the CurrentShape in the Grid's Brick composition. Update the Grid2D.
   *
   * @param s The CurrentShape setted in.
   */
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
    updateObserver(tGrid);
  }

  /**
   * Debug method. Print the grid's composition.
   */
  private void printGrid() {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        System.out.print(tGrid[i][j].getNb());
      }
      System.out.println();
    }

    System.out.println();
    System.out.println();
  }

  /**
   * Click a random Brick in the Grid.
   *
   * @return The char of the clicked Brick.
   */
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

  /**
   * Set the coordsLastBrickClicked to null value.
   *
   * @see Grid#coordsLastBrickClicked
   */
  public void setNoLastBrickClicked() {
    if (coordsLastBrickClicked != null) {
      for (int i = 0; i < tabBrickClicked.size(); ++i) {
        tabBrickClicked.get(i).setNewClickable(true);
      }
      coordsLastBrickClicked = null;
    }
  }

  /**
   * Set all the clicked Brick to be destroyed
   */
  public void setBricksToDestroy() {
    for (int i = 0; i < tabBrickClicked.size(); ++i) {
      tabBrickClicked.get(i).setToDestroy();
    }
    clearTabBrickClicked();
  }

  /**
   * Clear the array of Brick clicked.
   */
  public void clearTabBrickClicked() {
    tabBrickClicked.clear();
  }

  /**
   * Declick all Brick clicked.
   */
  public void declickedAllBrick() {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        tGrid[i][j].setClicked(false);
        tGrid[i][j].setNewClickable(false);
      }
    }
  }

  /**
   * Destroy all selected Brick who is in a right word. Update the Grid2D.
   */
  public void destroyAllSelectedBrickInWord() {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        if (tGrid[i][j].isClicked() && tGrid[i][j].gonnaBeDestroyed()) {
          doGravityOnBrick(i, j);
        }
      }
    }
    updateObserver(tGrid);
  }

  /**
   * Double click all Brick who are clicked in the Grid expect those passed in
   * param. Use for Worddle mode.
   *
   * @param b The Brick who isn't double clicked.
   */
  public void doubleClickedAllBrickClicked(Brick b) {
    for (int i = 0; i < sizeY; ++i) {
      for (int j = 0; j < sizeX; ++j) {
        if (tGrid[i][j].isClicked() && tGrid[i][j] != b) {
          tGrid[i][j].setClicked(true);
        }
      }
    }
  }

  /**
   * Get Brick's x and y coordinates in the Grid.
   *
   * @param b The Brick's coordinates wanted.
   * @return The coordinates of the Brick in the Grid.
   */
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

  /**
   * Do the Gravity on all Brick in the Grid beetween y and the top of the Grid
   * in a single column.
   *
   * @param y The line to begin
   * @param x The column concerned
   */
  public void doGravityOnBrick(int y, int x) {
    while (y > 1) {
      tGrid[y][x] = new Brick(tGrid[y - 1][x]);
      --y;
    }
  }

  /**
   * Determine accessible translation for a Shape orientation.
   *
   * @param shape The Shape
   * @return [0] -> value 1 if the move is acceptable, 0 if not. <br /> [1] ->
   * minX of the Grid accessible by the Shape orientation. <br /> [2] -> maxX of
   * the Grid accessible by the Shape orientation
   */
  public int[] DetermineAccessibleTranslationsForShapeOrientation(CurrentShape shape) {
    // Clear Results.
    int moveIsPossible = 0;
    int minDeltaX = 0;
    int maxDeltaX = 0;

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

  /**
   * Test if the CurrentShape's position is an acceptable position.
   *
   * @param shape The CurrentShape to test
   * @return 0 if the position is not acceptable, 1 if it is.
   */
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

  /**
   * Drop down the CurrentShape and add this in the Grid.
   *
   * @param tmpShape The CurrentShape to drop and add.
   */
  public void FullDropAndAddShapeToGrid(CurrentShape tmpShape) {
    int finalLine = tmpShape.getY();
    while (!tmpShape.tryCollision(getTGrid(), tmpShape.getX(), finalLine)) {
      ++finalLine;
    }
    tmpShape.move(tmpShape.getX(), finalLine - 1);
    setIn(tmpShape);
    //printGrid();
  }

  /**
   * Get the column height.
   *
   * @param x The column
   * @return The height of the column
   */
  public int getHeightColAt(int x) {
    for (int y = 0; y < sizeY; ++y) {
      if (tGrid[y][x].getNb() > 0) {
        return sizeY - y;
      }
    }

    return 0;
  }

  /**
   * Get the heighest column of the Grid.
   *
   * @return The value of the heighest column of the Grid.
   */
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

  /**
   * Sum the height of all column of the Grid.
   *
   * @return The sum of all column of the Grid.
   */
  public int SumOfWellHeights() {
    int totalWeightedCells = 0;

    for (int x = 0; x < sizeX; ++x) {
      totalWeightedCells += getHeightColAt(x);
    }

    return totalWeightedCells;
  }

  /**
   * Get all letter from a removed line.
   *
   * @return A string of all the letter from the removed line
   */
  public StringBuilder getAllLetterFromTheRemovedLine() {
    StringBuilder sb = new StringBuilder();
    int line = getFirstFullLine();
    for (int j = 0; j < sizeX; ++j) {
      sb.append(getTGrid()[line][j].getLetter());
    }
    return sb;
  }

  /**
   * Select a Brick at a specific line with a specific char.
   *
   * @param y The specific line
   * @param charAt The specifid char
   */
  void selectBrick(int y, char charAt) {
    for (int i = 0; i < sizeX; ++i) {
      if (tGrid[y][i].getLetter() == charAt && !tGrid[y][i].isClicked()) {
        tGrid[y][i].setClicked(true);
        return;
      }
    }
  }

  /**
   * The timer to display a CurrentModifier
   */
  private void displayModifier() {
    timerBeforeModifier = new Timer();

    timerBeforeModifier.schedule(new TimerTask() {
      @Override
      public void run() {
        System.out.println("New Modifier");
        currentModifier = new CurrentModifier(tGrid);
        setCurrentModifier(currentModifier);
      }
    }, 10000, 35000);

    timerBeforeModifier.schedule(new TimerTask() {
      @Override
      public void run() {
        System.out.println("Delete modifier");
        setCurrentModifier(null);
      }
    }, 35000);

  }

  @Override
  public void addObserver(Observer obs) {
    listObserver.add(obs);
  }

  @Override
  public void updateObserver(Object args) {
    for (Observer obs : listObserver) {
      obs.update(this, args);
    }
  }

  @Override
  public void delAllObserver() {
    listObserver = new ArrayList<>();
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

}
