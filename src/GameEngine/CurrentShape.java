package GameEngine;

public class CurrentShape extends Shape {

  private int curX, curY;
  private Brick[][] composition;

  public CurrentShape(String name, int[] color, int[][] representation) {
    super(name, color, representation);
    curX = 3;
    curY = -1;
    setComposition(representation);
  }

  CurrentShape(Shape s) {
    this(s.name, s.color, s.representation);
  }

  public int getX() {
    return curX;
  }

  public int getY() {
    return curY;
  }

  private void setComposition(int[][] representation){
    this.composition = new Brick[representation.length][representation[0].length];
    for (int i = 0; i < representation.length; ++i) {
      for (int j = 0; j < representation[i].length; ++j) {
        if (representation[i][j] > 0) { //suivant comment est implémenter la représentation
          composition[i][j] = new Brick('a', 1); //rajouter une lettre au hasard
        }
      }
    }
  }
  
  public Brick[][] getComposition() {
    return composition;
  }

  public void rotateLeft() {
    int[][] repTmp = new int[4][4];
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        repTmp[i][j] = representation[3 - j][i];
        /*
        //test if we can rotate
        if(repTmp[i][j] > 0){
          if(curX + j >= 10){
            return;
          }
        }
        */
      }
    }

    repTmp = replaceToTopLeftCorner(repTmp);

    //Vérifié ici s'il y a une collision
    //Sinon si yen a pas 
    representation = repTmp;
    setComposition(representation);

    //Pour replacer la pièce danbs la grid si lors de la rotation elle se met à dépasser (exemple de la barre en bas)
    //A VERIFIER SUR UN VRAI TETRIS OU ALORS A SUPPRIMER UNE FOIS LA COLLISION TESTE AU MOINS EN Y
    while (curX >= (10 - getMaxX(representation))) {
      --curX;
    }
    while (curY > (20 - getMaxY(representation))) {
      --curY;
    }
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

  public void tryMove(int newX, int newY) {
    curX = newX;
    curY = newY;
  }

  public boolean tryCollision(Brick[][] g, int newX, int newY) {
    if (newX < 0 || newX >= (10 - getMaxX(representation))) {
      return true;
    }
    if (newY >= (20 - getMaxY(representation))) {
      return true;
    }

    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        int value = representation[i][j];
        //On teste s'il y a déjà un élément dans la grid
        if (value > 0 && g[newY + i][newX + j].getNb() >= 1) {
          return true;
        }
      }
    }

    return false;
  }
}
