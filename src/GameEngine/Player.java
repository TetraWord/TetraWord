package GameEngine;

import GameEngine.Dictionnary.Dictionnary;
import Pattern.Observable;
import Pattern.Observer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

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
  private final Dictionnary dico;
  private Modifier modifier = null;
  private CurrentModifier currentModifier = null;
  private boolean worddle = false;
  private Timer timerBeforeWorddle = null;
  private Timer timerBeforeModifier = null;
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
    loadOptions();
    displayModifier();
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
    updateObservateur(null);
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
        if(currentModifier!= null && s.tryCollision(currentModifier,  s.getX(), s.getY())){
        	this.modifier = new Modifier(this.currentModifier);
					updateObservateur(this.modifier);
        	this.currentModifier = null;
        	boardGame.getGrid().setCurrentModifier(currentModifier);
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
      
      if(currentModifier!= null && s.tryCollision(currentModifier,  s.getX(), s.getY())){
      	this.modifier = new Modifier(this.currentModifier);
      	System.out.println("catch modifier");
      	this.currentModifier = null;
      	boardGame.getGrid().setCurrentModifier(currentModifier);
      }

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
    if (level < 10) {
      ++level;
    }
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

  public void verifAnagram(String bestWord) {
    
      System.out.println("word : "+getWord());
    if (dico.included(getWord())) {
      updateObservateur("Mot existant");
      if (getWord().equals(bestWord) || getWord().length() >= bestWord.length()) {
        addToScore(1000);
        //System.out.println("Le meilleur mot a ete trouve");
        updateObservateur("1000 points !");
      } else {
        addToScore(getWord().length() * 50);
        updateObservateur("Meilleur mot : " + bestWord);
      }
    } else {
      addToScore(-(getScore() % 1000));
      updateObservateur("Non Existant");
      updateObservateur("Meilleur mot : " + bestWord);
    }
  }

  public void doAnagram() {
    boardGame.getGrid().setAllowClick(true);
    int numLinesRemoved = 0;
    while (boardGame.getGrid().getFirstFullLine() != -1) {
      if (wordFinish) {
        StringBuilder sb = boardGame.getAllLetterFromTheRemovedLine();
        String bestWord = dico.findBestAnagramm(sb);
        verifAnagram(bestWord);
        clearWord();
        boardGame.removeLine();
        ++numLinesRemoved;
      }
    }
    finishAnagram(numLinesRemoved);
  }

  public void finishAnagram(int numLinesRemoved) {
    switchToAnagram(false);

    if (numLinesRemoved == 4) {
      System.out.println("Tetris ! ");
      addToScore(1000);
    } else {
      addToScore(numLinesRemoved * 100);
    }

    numLinesTotalRemoved += numLinesRemoved;
    updateObservateur(null);
    boardGame.getGrid().setAllowClick(false);
    boardGame.launchNextShape();
  }

  public void setWordFinish() {
    wordFinish = true;
    updateObservateur(null);
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
        updateObservateur("Mot Existant !");
        boardGame.getGrid().setBricksToDestroy();
        addToScore(s.length() * 3);
      } else {
        updateObservateur("Non Existant");
        boardGame.getGrid().clearTabBrickClicked();
        addToScore(-s.length() * 4);
      }
      clearWord();
      boardGame.getGrid().setAllowDoubleClick(true);
      updateObservateur(null);
    } else if (!GameEngine.getInstance().timerWorddleIsAlive()) {
      finishWorddle();
    }
  }

  public void finishWorddle() {
    boardGame.finishWorddle(currentShapeStocked);
    clearWord();
    updateObservateur(null);
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
  
  public void displayModifier() {
  	 timerBeforeModifier = new Timer();
  	 
  	 timerBeforeModifier.schedule(new TimerTask() {
  		 @Override
  		 public void run() {
  			 System.out.println("New Modifier");
  			 currentModifier = new CurrentModifier(boardGame.getGrid().getTGrid());
  		   boardGame.getGrid().setCurrentModifier(currentModifier);
  		 }
  	 }, 30000, 65000);
  	 
  	 timerBeforeModifier.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Delete modifier");
				currentModifier = null;
				boardGame.getGrid().setCurrentModifier(currentModifier);
			}
  	 }, 35000);
  	
  }

  public boolean hasModifier() {
    return modifier != null;
  }

  public void activeModifier() {
    modifier.active(this);
    modifier = null;
		updateObservateur(modifier);
  }
  
  /*public void throwModifier() {
  	modifier.active(Adversaire);
    modifier = null;
  }*/
  
  public void shake(int offsetX, int offsetY) {
		boardGame.setOffset(offsetX, offsetY);
		int[] offset = new int[2];
		offset[0] = offsetX;
		offset[1] = offsetY;
		updateObservateur(offset);
	}
}
