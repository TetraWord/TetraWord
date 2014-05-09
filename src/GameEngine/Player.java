package GameEngine;

import GameEngine.Dictionnary.Dictionary;
import Pattern.Observable;
import Pattern.Observer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;

public class Player implements Observable {

  protected final BoardGame boardGame;
  private String name = "player";
  private int speedFall;
  private int speedFallInit;
  private final int number;
  private int level;
  private InGameState state = InGameState.TETRIS;
  private boolean wordFinish = false;
  private final StringBuilder word = new StringBuilder();
  private int score;
  private int numLinesTotalRemoved;
  private Shape shapeStocked;
  private CurrentShape currentShapeStocked = null;
  private boolean switchShape = false;
  private final Object monitor = new Object();
  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final Dictionary dico;
  private Modifier modifier = new Modifier("Shake");
  private boolean worddle = false;
  private Timer timerBeforeWorddle = null;
  private long t = 0;

  public Player(int nb, Shape s, Shape s2, Dictionary d) {
    boardGame = new BoardGame(s, s2, this);
    score = 0;
    level = 1;
    numLinesTotalRemoved = 0;
    number = nb;
    dico = d;
    speedFall = 1000;
    speedFallInit = speedFall;
    loadOptions();
  }

  private void loadOptions() {
    Properties prop = new Properties();
    InputStream input = null;

    /*Get background image from file design*/
    try {
      input = new FileInputStream("conf/options.properties");

      // load a properties file
      prop.load(input);

      speedFallInit = Integer.parseInt(prop.getProperty("speed"));

    } catch (IOException ex) {
      /*Default background*/
      speedFallInit = 1000;
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Shape useShapeStocked() {
    Shape s = shapeStocked;
    shapeStocked = null;
    return s;
  }

  public boolean hasShapeStocked() {
    return shapeStocked != null;
  }

  public boolean canSwitchShape() {
    return switchShape;
  }

  public void canSwitchShape(boolean b) {
    switchShape = b;
  }

  public Shape getStockShape() {
    return shapeStocked;
  }

  public void stockShape(CurrentShape cs) {
    if (shapeStocked == null) {
      shapeStocked = new Shape(cs, true);
      switchShape = false;
    }
    updateObserver(null);
  }

  public int getNumber() {
    return number;
  }

  public int getNumLinesTotalRemoved() {
    return numLinesTotalRemoved;
  }

  public Dictionary getDico() {
    return dico;
  }

  private void tryModifierCollision(CurrentShape s) {
    CurrentModifier cm = getGrid().getCurrentModifier();
    if (cm != null && s.tryCollision(cm, s.getX(), s.getY())) {
      this.modifier = new Modifier(cm);
      updateObserver(this.modifier);
      getGrid().setCurrentModifier(null);
    }
  }

  public void down(int step) {
    synchronized (monitor) {
      CurrentShape s = getCurrentShape();
      if (s != null) {
        int tmpY = s.getY();
        if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX(), s.getY() + step)) {
          s.move(s.getX(), s.getY() + step);
        }
        tryModifierCollision(s);
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
      tryModifierCollision(s);
    }
  }

  public void right() {
    CurrentShape s = getCurrentShape();
    if (s != null) {
      if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX() + 1, s.getY())) {
        s.move(s.getX() + 1, s.getY());
      }
      tryModifierCollision(s);
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

      tryModifierCollision(s);

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

  public Grid getGrid() {
    return boardGame.getGrid();
  }

  protected CurrentShape getCurrentShape() {
    return boardGame.getGrid().getCurrentShape();
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
    updateObserver(null);
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
    if (level < 10) {
      ++level;
    }
    setNewSpeedFall(speedFallInit - level * 83);
    updateObserver(null);
  }

  public void switchToAnagram(boolean b) {
    wordFinish = b != true;
    state = b ? InGameState.ANAGRAMME : InGameState.TETRIS;
    updateObserver(null);
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
    updateObserver(null);
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
    updateObserver(null);
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

  public void verifAnagram(String bestWord) {
    if (dico.included(getWord())) {
      updateObserver("Mot existant");
      if (getWord().equals(bestWord) || getWord().length() >= bestWord.length()) {
        addToScore(1000);
        //System.out.println("Le meilleur mot a ete trouve");
        updateObserver("1000 points !");
      } else {
        addToScore(getWord().length() * 50);
        updateObserver("Meilleur mot : " + bestWord);
      }
    } else {
      addToScore(-(getScore() % 1000));
      updateObserver("Non Existant");
      updateObserver("Meilleur mot : " + bestWord);
    }
  }

  public void doAnagram() {
    boardGame.getGrid().setAllowClick(true);
    int numLinesRemoved = 0;
    while (boardGame.getGrid().getFirstFullLine() != -1) {
      updateObserver(null);
      if (wordFinish) {
        StringBuilder sb = getGrid().getAllLetterFromTheRemovedLine();
        String bestWord = dico.findBestAnagramm(sb);
        verifAnagram(bestWord);
        clearWord();
        getGrid().removeLine(getGrid().getFirstFullLine());
        ++numLinesRemoved;
      }
    }
    finishAnagram(numLinesRemoved);
  }

  public void finishAnagram(int numLinesRemoved) {
    switchToAnagram(false);

    if (numLinesRemoved == 4) {
      addToScore(1000);
    } else {
      addToScore(numLinesRemoved * 100);
    }

    numLinesTotalRemoved += numLinesRemoved;
    updateObserver(null);
    boardGame.getGrid().setAllowClick(false);
    boardGame.launchNextShape();
  }

  public void setWordFinish() {
    wordFinish = true;
    updateObserver(null);
  }

  public void switchToWorddle(boolean b) {
    if (!b) {
      GameEngine.getInstance().finishTimerWorddle();
      startTimerBeforeWorddle();
    }
    wordFinish = b != true;
    boardGame.getGrid().setAllowClick(b);
    boardGame.getGrid().setNoLastBrickClicked();
    state = b ? InGameState.WORDDLE : InGameState.TETRIS;
    updateObserver(null);
  }

  public void stockCurrentShape() {
    currentShapeStocked = boardGame.getGrid().getCurrentShape();
    boardGame.getGrid().setCurrentShape(null);
  }

  public void doWorddle() {
    if (wordFinish && GameEngine.getInstance().timerWorddleIsAlive()) {
      String s = getWord();
      if (dico.included(s)) {
        updateObserver("Mot Existant !");
        boardGame.getGrid().setBricksToDestroy();
        getGrid().setBricksToDestroy();
        addToScore(s.length() * 10);
      } else {
        updateObserver("Non Existant");
        boardGame.getGrid().clearTabBrickClicked();
        getGrid().clearTabBrickClicked();
        addToScore(-s.length() * 5);
      }
      clearWord();
      boardGame.getGrid().setAllowDoubleClick(true);
      updateObserver(null);
    } else if (!GameEngine.getInstance().timerWorddleIsAlive()) {
      finishWorddle();
    }
  }

  public void finishWorddle() {
    getGrid().finishWorddle(currentShapeStocked);
    clearWord();
    updateObserver(null);
    state = InGameState.TETRIS;
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

  public Modifier getModifier() {
    return modifier;
  }

  public boolean hasModifier() {
    return modifier != null;
  }

  public void activeModifier() {
    modifier.active(this);
    modifier = null;
		updateObserver(modifier);
  }

  public void shake(int offsetX, int offsetY) {
		boardGame.setOffset(offsetX, offsetY);
		int[] offset = new int[2];
		offset[0] = offsetX;
		offset[1] = offsetY;
		updateObserver(offset);
	}

  public void stopAllTimers() throws InterruptedException {
  }
}
