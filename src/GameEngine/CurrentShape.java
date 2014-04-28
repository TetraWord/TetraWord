package GameEngine;

import java.awt.Color;

public class CurrentShape extends Shape {

  private int curX, curY;

  public CurrentShape(String name, Color color, int[][] representation, Brick[][] composition) {
    super(name, color, representation, composition);
    curX = 3;
    curY = 0;
  }

  public CurrentShape(Shape s) {
    this(s.name, s.color, s.representation, s.composition);
  }

  public int getX() {
    return curX;
  }

  public int getY() {
    return curY;
  }

  public Brick[][] getComposition() {
    return composition;
  }

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

    //Vérifier ici s'il y a une collision
    if (!tryCollision(g, curX, curY, repTmp)) {
      //Sinon si yen a pas 
      representation = repTmp;
      composition = compTmp;

      //Pour replacer la pièce danbs la grid si lors de la rotation elle se met à dépasser (exemple de la barre en bas)
      //A VERIFIER SUR UN VRAI TETRIS OU ALORS A SUPPRIMER UNE FOIS LA COLLISION TESTE AU MOINS EN Y
      while (curX >= (Grid.sizeX - getMaxX(representation))) {
        --curX;
      }
      while (curY > (Grid.sizeY - getMaxY(representation))) {
        --curY;
      }
    }

    updateObservateur(null);
  }

  public int getMaxX(int[][] matrix) {
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

  public int getMaxY(int[][] matrix) {
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

  public int getMinY(Brick[][] matrix) {
    int finalLine = new Integer(getY());

    while (!tryCollision(matrix, getX(), finalLine)) {
      ++finalLine;
    }

    return finalLine - 1;
  }

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

  public void move(int newX, int newY) {
    curX = newX;
    curY = newY;
  }

  /* TryCollision pour les mouvements left, right, down */
  public boolean tryCollision(Brick[][] g, int newX, int newY) {
    if (newX < 0 || newX >= (Grid.sizeX - getMaxX(representation))) {
      return true;
    }
    if (newY >= (Grid.sizeY - getMaxY(representation))) {
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

  /* TryCollision pour la rotation */
  public boolean tryCollision(Brick[][] g, int newX, int newY, int[][] rep) {
    if (newX < 0 || newX >= (Grid.sizeX - getMaxX(rep))) {
      return true;
    }
    if (newY < 0 || newY >= (Grid.sizeY - getMaxY(rep))) {
      return true;
    }

    for (int i = 0; i < sizeShape; ++i) {
      for (int j = 0; j < sizeShape; ++j) {
        int value = rep[i][j];
        //On teste s'il y a déjà un élément dans la grid
        if (value > 0 && g[newY + i][newX + j].getNb() >= 1) {
          return true;
        }
      }
    }

    return false;
  }

}
