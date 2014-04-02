package GameEngine;

public class Player {

  private final BoardGame boardGame;
  private final int number;
  private double score;
  private int numLinesRemoved;
  private Shape shapeStocked;

  public Player(int nb, Shape s) {
    boardGame = new BoardGame(nb, s);
    score = 0;
    numLinesRemoved = 0;
    number = nb;
  }

  public void addShapeStocked(Shape s) {
    if (shapeStocked != null) {
      shapeStocked = s;
    }
  }

  public Shape useShapeStocked() {
    Shape s = shapeStocked;
    shapeStocked = null;
    return s; //vérifier si ca ne retourne pas toujours null... 
  }

  public int getNumber() {
    return number;
  }

  public synchronized void down() {
    CurrentShape s = getCurrentShape();
    int tmpY = s.getY();
    if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX(),  s.getY()+1)) {
      s.tryMove(s.getX(), s.getY() + 1);
    }
    //Si on ne peut pas faire descendre la pièce plus bas, on l'inscrit dans la Grid
    if (tmpY == s.getY()) {
      Grid g = boardGame.getGrid();
      boardGame.setInGrid(s);
    }
  }

  public void left() {
    CurrentShape s = getCurrentShape();
    if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX()-1,  s.getY())) {
    	s.tryMove(s.getX() - 1, s.getY());
    }
  }

  public void right() {
    CurrentShape s = getCurrentShape();
    if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX()+1, s.getY())) {
    	s.tryMove(s.getX() + 1, s.getY());
    }
  }

  public void rotate() {
    CurrentShape s = getCurrentShape();
    s.rotateLeft();
  }

  public BoardGame getBoardGame() {
    return boardGame;
  }

  private CurrentShape getCurrentShape() {
    return boardGame.getGrid().getCurrentShape();
  }
}
