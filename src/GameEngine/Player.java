package GameEngine;

import GameEngine.Dictionnary.Dictionnary;
import Pattern.Observable;
import Pattern.Observer;

import java.util.ArrayList;
import java.util.Timer;

public class Player implements Observable {

  private final BoardGame boardGame;
  private int speedFall;
  private final int speedFallInit;
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
  private Modifier modifier = new Modifier("Bomb");
  private boolean worddle = false;
  private Timer timerBeforeWorddle = null;
  private long t = 0;

  public Player(int nb, Shape s, Shape s2, Dictionnary d) {
    boardGame = new BoardGame(nb, s, s2, this);
    score = 0;
    level = 1;
    numLinesTotalRemoved = 0;
    number = nb;
    dico = d;
    speedFall = 1000;
    speedFallInit = speedFall;
    startTimerBeforeWorddle();
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
  
  public int getNumLinesTotalRemoved() {
    return numLinesTotalRemoved;
  }

  public Dictionnary getDico() {
    return dico;
  }

  public void down(int step) {
    synchronized (monitor) {
      CurrentShape s = getCurrentShape();
      if (s != null) {
        int tmpY = s.getY();
        if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX(), s.getY() + step)) {
          s.move(s.getX(), s.getY() + step);
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
  
  public Shape getStockShape() {
    return shapeStocked;
  }

  public void stockShape(CurrentShape cs) {
    if (shapeStocked == null) {
      shapeStocked = new Shape(cs, true);
    }
    updateObservateur(null);
  }

  public int getScore() {
    return score;
  }

  public void addToScore(int add) {
    score = score + add;
   //score += 1100;
    if ((int) score / 1000 >= level) {
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

	int getNbLines() {
		return numLinesTotalRemoved;
	}

  public int getSpeedFall() {
    return speedFall;
  }
  
  public void setLevelUp() {
    ++level;
    setNewSpeedFall(speedFallInit - level * 83);
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
    updateObservateur(null);
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
    updateObservateur(null);
    boardGame.launchNextShape();
  }

  public void setWordFinish() {
    wordFinish = true;
    updateObservateur(null);
  }

  public void switchToWorddle(boolean b) {
    if(!b){
      GameEngine.getInstance().finishTimerWorddle();
      startTimerBeforeWorddle();
    }
    wordFinish = b != true;
    boardGame.setAllowClick(b);
    boardGame.setNoLastBrickClicked();
    state = b ? InGameState.WORDDLE : InGameState.TETRIS;
    updateObservateur(null);
  }

  public void stockCurrentShape() {
    currentShapeStocked = boardGame.getGrid().getCurrentShape();
    boardGame.getGrid().setCurrentShape(null);
  }

  public void doWorddle() {
    if (wordFinish && GameEngine.getInstance().timerWorddleIsAlive()) {
      String s = getWord();
      if (dico.included(s)) {
        System.out.println("Ce mot fait partie du dico bravo");
        boardGame.setBricksToDestroy();
        addToScore(s.length() * 3);
      } else {
        System.out.println("Mot n'est pas dans le dico");
        boardGame.clearTabBrickClicked();
        addToScore(-s.length() * 4);
      }
      clearWord();
      boardGame.setAllowDoubleClick(true);
      updateObservateur(null);
    } else if(!GameEngine.getInstance().timerWorddleIsAlive()) {
      boardGame.setAllowDoubleClick(false);
      boardGame.getGrid().setCurrentShape(currentShapeStocked);
      boardGame.getGrid().destroyAllSelectedBrickInWord();
      boardGame.getGrid().declickedAllBrick();
      clearWord();
      updateObservateur(null);
    }
  }
  
  public final void startTimerBeforeWorddle() {
    timerBeforeWorddle = new Timer();
    timerBeforeWorddle.schedule(new WorddleTimerTask((this)), 30000);
    t = System.nanoTime();
    setWorddle(false);
  }
  
  public boolean canWorddle() {
    return worddle;
  }

  public void setWorddle(boolean b) {
    worddle = b;
  }
  
  public long getTime() {
    return System.nanoTime() - t;
  }
  
  public void setNewSpeedFall(int s) {
    speedFall = s;
  }

  public int getSpeedFallInit() {
    return speedFallInit;
  }

  public boolean hasModifier() {
    return modifier != null;
  }

  public void activeModifier() {
    modifier.active(this);
    modifier = null;
  }
}
