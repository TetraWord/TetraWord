package GameEngine;

public class Player {

  private final BoardGame boardGame;
  private final int number;
  private int level;
  private double score;
  private int numLinesRemoved;
  private Shape shapeStocked;
  private final Object monitor = new Object();

  public Player(int nb, Shape s, Shape s2) {
    boardGame = new BoardGame(nb, s, s2, this);
    score = 0;
    level = 1;
    numLinesRemoved = 0;
    number = nb;
  }

  public Shape useShapeStocked() {
    Shape s = shapeStocked;
    shapeStocked = null;
    return s; //vérifier si ca ne retourne pas toujours null... 
  }

  public boolean hasShapeStocked() {
    return shapeStocked != null;
  }

  public int getNumber() {
    return number;
  }

  public void down() {
    synchronized (monitor) {
      CurrentShape s = getCurrentShape();
      int tmpY = s.getY();
      if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX(), s.getY() + 1)) {
        s.move(s.getX(), s.getY() + 1);
      }
      //Si on ne peut pas faire descendre la pièce plus bas, on l'inscrit dans la Grid
      if (tmpY == s.getY()) {
        Grid g = boardGame.getGrid();
        boardGame.setInGrid(s);
      }
    }
  }

  public void left() {
    CurrentShape s = getCurrentShape();
    if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX() - 1, s.getY())) {
      s.move(s.getX() - 1, s.getY());
    }
  }

  public void right() {
    CurrentShape s = getCurrentShape();
    if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX() + 1, s.getY())) {
      s.move(s.getX() + 1, s.getY());
    }
  }

  public void dropDown() {
    CurrentShape s = getCurrentShape();
    int finalLine = 0;
    while (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX(), finalLine)) {
      ++finalLine;
    }

    s.move(s.getX(), finalLine - 1);
  }

  public void rotate() {
    CurrentShape s = getCurrentShape();
    s.rotateLeft(boardGame.getGrid().getTGrid());
  }

  public BoardGame getBoardGame() {
    return boardGame;
  }

  private CurrentShape getCurrentShape() {
    return boardGame.getGrid().getCurrentShape();
  }

  public void stockShape() {
    if (shapeStocked != null) {
      shapeStocked = getCurrentShape();
    }
  }

  public int getLevel() {
    return level;
  }

  public void setLevelUp() {
    ++level;
  }

}
