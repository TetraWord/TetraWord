package GameEngine;

import java.awt.Color;

/**
 * <b> CurrentShape is a logical part of the game representing the Shape falling in the Grid.</b>
 * <p>
 * The CurrentShape inherits from the Shape. </p>
 * <p>
 * The CurrentShape contains :
 * <ul>
 * <li> His horizontal position in the Grid </li>
 * <li> His vertical position in the Grid </li>
 * </ul>
 * </p>
 *
 * @see Shape
 *
 */
public class CurrentShape extends Shape {

  /**
   * The position of the CurrentShape in the Grid
   * 
   * @see CurrentShape#getX() 
   * @see CurrentShape#getY()
   * @see CurrentShape#move(int, int) 
   */
  private int curX, curY;

  /**
   * Private CurrentShape constructor. 
   * Call the Shape constructor
   * Initialize the position of the CurrentShape to (3,0).
   * 
   * @param name The name of the Shape
   * @param color The Color of the Shape
   * @param representation The representation of the Shape
   * @param composition The Brick composition of the Shape
   * 
   * @see Shape#Shape(java.lang.String, java.awt.Color, int[][], GameEngine.Brick[][]) 
   */
  private CurrentShape(String name, Color color, int[][] representation, Brick[][] composition) {
    super(name, color, representation, composition);
    curX = 3;
    curY = 0;
  }

  /**
   * CurrentShape constructor.
   * Call the private constructor of CurrentShape.
   * 
   * @param s The Shape who become a CurrentShape
   * @see CurrentShape#CurrentShape(java.lang.String, java.awt.Color, int[][], GameEngine.Brick[][]) 
   */
  public CurrentShape(Shape s) {
    this(s.name, s.color, s.representation, s.composition);
  }

  /**
   * Get the horizontal position of the CurrentShape.
   * @return The horizontal position.
   * @see CurrentShape#curX
   */
  public int getX() {
    return curX;
  }

  /**
   * Get the vertical position of the CurrentShape.
   * @return The vertical position.
   * @see CurrentShape#curY
   */
  public int getY() {
    return curY;
  }
  
  /**
   * Set the CurrentShape new position.
   * 
   * @param newX The new horizontal position.
   * @param newY The new vertical position.
   * @see CurrentShape#curX
   * @see CurrentShape#curY
   */
  public void move(int newX, int newY) {
    curX = newX;
    curY = newY;
  }
  
  
  /**
   * Test if the CurrentShape collides with an other Shape in the Grid.
   * Method called for down(), left(), right() and dropDown() method from Player().
   * 
   * @param g The brick composition of the CurrentShape
   * @param newX The horizontal position of the CurrentShape wanted
   * @param newY The vertical position of the CurrentShape wanted
   * @return true if there is collision, false if not.
   */
  public boolean tryCollision(Brick[][] g, int newX, int newY) {
    if (newX < 0 || newX >= (Grid.sizeX - getMaxWidth(representation))) {
      return true;
    }
    if (newY >= (Grid.sizeY - getMaxHeight(representation))) {
      return true;
    }

    for (int i = 0; i < sizeShape; ++i) {
      for (int j = 0; j < sizeShape; ++j) {
        int value = representation[i][j];
        //On teste s'il y a déjà un élément dans la grid
        if (value > 0 && g[newY + i][newX + j].getNb() >= 1) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Test if the CurrentShape collides with a Modifier in the Grid.
   * 
   * @param m The CurrentModifier in the Grid.
   * @param newX The new horizontal position of the CurrentShape
   * @param newY The new vertical position of the CurrentShape
   * @return true if there is collision, false if not.
   */
  public boolean tryCollision(CurrentModifier m, int newX, int newY ) {
  	int X = m.getX();
  	int Y = m.getY();
  	
  	for (int i = 0; i < sizeShape; ++i) {
      for (int j = 0; j < sizeShape; ++j) {
        int value = representation[i][j];
        //On teste s'il y a déjà un élément dans la grid
        if (value > 0 && newX + j == X && newY + i == Y) {
          return true;
        }
      }
    }
  	
  	return false;
  }
  
  /**
   * Rotate the CurrentShape.
   * 
   * @param g The Brick composition of the CurrentShape
   */
  public void rotateLeft(Brick[][] g) {
    int[][] repTmp = new int[sizeShape][sizeShape];
    Brick[][] compTmp = new Brick[sizeShape][sizeShape];

    for (int i = 0; i < sizeShape; ++i) {
      for (int j = 0; j < sizeShape; ++j) {
        repTmp[i][j] = representation[sizeShape - 1 - j][i];
        compTmp[i][j] = composition[sizeShape - 1 - j][i];
      }
    }

    repTmp = replaceToTopLeftCorner(repTmp);
    compTmp = replaceToTopLeftCorner(compTmp);
    
    int[][] prevRepresentation = (this.representation).clone();
    this.representation = repTmp.clone();
    
    if (!tryCollision(g, curX, curY)) {
      representation = repTmp;
      composition = compTmp;

      while (curX >= (Grid.sizeX - getMaxWidth(representation))) {
        --curX;
      }
      while (curY > (Grid.sizeY - getMaxHeight(representation))) {
        --curY;
      }
    }else {
      representation = prevRepresentation.clone();
    }

    updateObservateur(null);
  }

  /**
   * Replace the Shape to the top left corner of the 4*4 representation.
   * 
   * @param matrix The Brick composition of the CurrentShape
   * @return The new Brick composition replace to the top left corner.
   */
  private Brick[][] replaceToTopLeftCorner(Brick[][] matrix) {
    boolean lineIsEmpty = true;
    boolean colIsEmpty = true;
    int firstLineNoEmpty = -1;
    int firstColNoEmpty = -1;

    for (int i = 0; i < matrix.length; ++i) {
      for (int j = 0; j < matrix[i].length; ++j) {
        if (matrix[i][j] != null) {
          lineIsEmpty = false;
        }
        if (matrix[j][i] != null) {
          colIsEmpty = false;
        }
      }
      if (!lineIsEmpty && firstLineNoEmpty == -1) {
        firstLineNoEmpty = i;
      } else if (!colIsEmpty && firstColNoEmpty == -1) {
        firstColNoEmpty = i;
      }
    }

    int prevValue = firstColNoEmpty;
    Brick[][] tmp = new Brick[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; ++i) {
      if (firstLineNoEmpty == matrix.length) {
        firstLineNoEmpty = 0;
      }
      tmp[i] = matrix[firstLineNoEmpty];
      ++firstLineNoEmpty;
    }

    Brick[][] tmp2 = new Brick[tmp.length][tmp[0].length];
    for (int i = 0; i < tmp.length; ++i) {
      for (int j = 0; j < tmp[i].length; ++j) {
        if (firstColNoEmpty == tmp[i].length) {
          firstColNoEmpty = 0;
        }
        tmp2[i][j] = tmp[i][firstColNoEmpty];
        ++firstColNoEmpty;
      }
      firstColNoEmpty = prevValue;
    }

    return tmp2;
  }

  /**
   * Replace the Shape to the top left corner of the 4*4 representation.
   * 
   * @param matrix The Shape representation
   * @return The new Shape representation replace to the top left corner.
   */
  private int[][] replaceToTopLeftCorner(int[][] matrix) {
    boolean lineIsEmpty = true;
    boolean colIsEmpty = true;
    int firstLineNoEmpty = -1;
    int firstColNoEmpty = -1;

    for (int i = 0; i < matrix.length; ++i) {
      for (int j = 0; j < matrix[i].length; ++j) {
        if (matrix[i][j] == 1) {
          lineIsEmpty = false;
        }
        if (matrix[j][i] == 1) {
          colIsEmpty = false;
        }
      }
      if (!lineIsEmpty && firstLineNoEmpty == -1) {
        firstLineNoEmpty = i;
      } else if (!colIsEmpty && firstColNoEmpty == -1) {
        firstColNoEmpty = i;
      }
    }

    int prevValue = firstColNoEmpty;
    int[][] tmp = new int[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; ++i) {
      if (firstLineNoEmpty == matrix.length) {
        firstLineNoEmpty = 0;
      }
      tmp[i] = matrix[firstLineNoEmpty];
      ++firstLineNoEmpty;
    }

    int[][] tmp2 = new int[tmp.length][tmp[0].length];
    for (int i = 0; i < tmp.length; ++i) {
      for (int j = 0; j < tmp[i].length; ++j) {
        if (firstColNoEmpty == tmp[i].length) {
          firstColNoEmpty = 0;
        }
        tmp2[i][j] = tmp[i][firstColNoEmpty];
        ++firstColNoEmpty;
      }
      firstColNoEmpty = prevValue;
    }

    return tmp2;
  }

  /**
   * Get the max width of the CurrentShape.
   * 
   * @param matrix The representation of the Shape.
   * @return the max width of the CurrentShape.
   */
  public int getMaxWidth(int[][] matrix) {
    boolean colIsEmpty = true;
    int n = matrix.length;

    for (int i = (n - 1); i >= 0; --i) {
      for (int j = 0; j < n; ++j) {
        if (matrix[j][i] == 1) {
          colIsEmpty = false;
        }
      }
      if (!colIsEmpty) {
        return i;
      }
    }
    return 0;
  }

  /**
   * Get the max height of the CurrentShape
   * 
   * @param matrix The representation of the Shape.
   * @return the max height of the CurrentShape.
   */
  public int getMaxHeight(int[][] matrix) {
    boolean rowIsEmpty = true;
    int n = matrix.length;

    for (int i = (n - 1); i >= 0; --i) {
      for (int j = 0; j < n; ++j) {
        if (matrix[i][j] == 1) {
          rowIsEmpty = false;
        }
      }
      if (!rowIsEmpty) {
        return i;
      }
    }
    return 0;
  }

  /**
   * Get the final line where the CurrentShape can go.
   * @param matrix The Brick composition of the CurrentShape.
   * @return the final line where the CurrentShape can go.
   */
  public int getFinalLine(Brick[][] matrix) {
    int finalLine = new Integer(getY());

    while (!tryCollision(matrix, getX(), finalLine)) {
      ++finalLine;
    }

    return finalLine - 1;
  }
  
}
