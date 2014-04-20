package GameEngine;

import GameEngine.Dictionnary.Dictionnary;
import Pattern.Observable;
import Pattern.Observer;

import java.util.ArrayList;

public class Player implements Observable {

  private final BoardGame boardGame;
  private final int number;
  private int level;
  private InGameState state = InGameState.TETRIS;
  private boolean wordFinish = false;
  private final StringBuilder word = new StringBuilder();
  private int score;
  private int numLinesTotalRemoved;
  private Shape shapeStocked;
  private CurrentShape currentShapeStocked = null;
  private final Object monitor = new Object();
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final Dictionnary dico;

  public Player(int nb, Shape s, Shape s2, Dictionnary d) {
    boardGame = new BoardGame(nb, s, s2, this);
    score = 0;
    level = 1;
    numLinesTotalRemoved = 0;
    number = nb;
    dico = d;
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
  
  public Dictionnary getDico() {
  	return dico;
  }

  public void down() {
    synchronized (monitor) {
      CurrentShape s = getCurrentShape();
      if (s != null) {
        int tmpY = s.getY();
        if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX(), s.getY() + 1)) {
          s.move(s.getX(), s.getY() + 1);
        }
        //Si on ne peut pas faire descendre la pièce plus bas, on l'inscrit dans la Grid
        if (tmpY == s.getY()) {
          boardGame.finishFall(s);
        }
      }
    }
  }

  public void left() {
    CurrentShape s = getCurrentShape();
    if (s != null) {
      if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX() - 1, s.getY())) {
        s.move(s.getX() - 1, s.getY());
      }
    }
  }

  public void right() {
    CurrentShape s = getCurrentShape();
    if (s != null) {
      if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX() + 1, s.getY())) {
        s.move(s.getX() + 1, s.getY());
      }
    }
  }

  public void dropDown() {
    CurrentShape s = getCurrentShape();
    if (s != null) {
      int finalLine = new Integer(s.getY());
      while (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX(), finalLine)) {
        ++finalLine;
      }
      int interval = finalLine - s.getY();
      this.addToScore(interval * 2);
      s.move(s.getX(), finalLine - 1);

      boardGame.finishFall(s);
    }
  }

  public void rotate() {
    CurrentShape s = getCurrentShape();
    if (s != null) {
      s.rotateLeft(boardGame.getGrid().getTGrid());
    }
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

  public int getScore() {
    return score;
  }

  public void addToScore(int add) {
    score = score + add;
    if((int)score / 1000 >= level){
      setLevelUp();
    }
    updateObservateur(null);
  }

  public InGameState getState() {
    return state;
  }

  public int getLevel() {
    return level;
  }

  public void setLevelUp() {
    ++level;
    updateObservateur(null);
  }

  public void switchToAnagram(boolean b) {
    wordFinish = b != true;
    state = b ? InGameState.ANAGRAMME : InGameState.TETRIS;
    updateObservateur(null);
  }

  public boolean isAnagram() {
    return state == InGameState.ANAGRAMME;
  }

  public boolean isTetris() {
    return state == InGameState.TETRIS;
  }

  public boolean isWorddle() {
    return state == InGameState.WORDDLE;
  }

  public boolean isFinish() {
    return state == InGameState.FINISH;
  }

  public void finish() {
    state = InGameState.FINISH;
  }

  public void addNewChar(char c) {
    word.append(c);
    updateObservateur(null);
  }

  public String getWord() {
    return word.toString();
  }
  
  public boolean isWordFinished() {
    return wordFinish;
  }

  void clearWord() {
    word.delete(0, word.length());
    wordFinish = false;
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

  public void doAnagram() {
    int numLinesRemoved = boardGame.removeFullLine();
    if (numLinesRemoved == 4) {
      System.out.println("Tetris ! ");
      addToScore(1000);
    } else {
      addToScore(numLinesRemoved * 100);
    }
    numLinesTotalRemoved += numLinesRemoved;
    boardGame.launchNextShape();
  }

  public void setWordFinish() {
    wordFinish = true;
  }

  public void switchToWorddle(boolean b) {
    if(!b){ boardGame.getGrid().setCurrentShape(currentShapeStocked); }
    boardGame.setAllowClick(b);
    state = b ? InGameState.WORDDLE : InGameState.TETRIS;
    updateObservateur(null);
  }

  public void stockCurrentShape() {
    currentShapeStocked = boardGame.getGrid().getCurrentShape();
    boardGame.getGrid().setCurrentShape(null);
  }
}
