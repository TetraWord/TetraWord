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

/**
 *  <b> Player is the logical engine of the game</b>.
 *  
 *  <p>
 * The player is observable by the Hud. 
 * </p>
 *  <p>
 *  A Player contains:
 *  <ul>
 *  <li> A final BoardGame, which is its own.</li>
 *  <li> A name or pseudo</li>
 *  <li> A speed of fall and a speed of fall initial</li>
 *  <li> A final number to know the number of player</li>
 *  <li> The level </li>
 *  <li> A state to know the current mode the player is playing</li>
 *  <li> A boolean to know if the player just enter a word </li>
 *  <li> A final StringBuilder to get the letter selected by the player </li>
 *  <li> A score </li>
 *  <li> The number of removed lines </li>
 *  <li> A Shape that it can be stocked and use later </li>
 *  <li> A CurrentShape </li>
 *  <li> A boolean to know if the player keep the CurrentShape and use the Shape stocked </li>
 *  <li> A monitor </li>
 *  <li> An ArrayList of his observer object </li>
 *  <li> A final Dictionary</li>
 *  <li> A Modifier</li>
 *  <li> A boolean to know if it's in worddle mode</li>
 *  <li> A Timer before worddle</li>
 *  <li> A long to keep in memory the time</li>
 *  </ul>
 *  </p>
 *  
 *  @see GameEngine
 *  @see Hud
 *
 */
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
	private Modifier modifier = null;
	private boolean worddle = false;
	private Timer timerBeforeWorddle = null;
	private long t = 0;

	/**
	 * Constructor Player
	 * <p>
	 * Create a BoardGame and initialize the display (score, level, number of removed lines, speed of fall) and the options
	 * </p>
	 * 
	 * @param nb: the number of the player
	 * @param s: The first Shape of the Game
   * @param s2: The Second Shape of the Game
	 * @param d: The player's dictionary
	 */
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

	/**
	 * Load the different options that can be changed
	 * (design, speed ...)
	 */
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

	/**
	 * Get the player's name/pseudo
	 * 
	 * @return the player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the player's name/pseudo
	 * 
	 * @param name: the new player's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Use the Shape stocked
	 * 
	 * @return the Shape stocked
	 */
	public Shape useShapeStocked() {
		Shape s = shapeStocked;
		shapeStocked = null;
		return s;
	}

	/**
	 * Verify if the Player has a Shape stocked
	 * 
	 * @return True if the Player has a Shape stocked, False otherwise
	 */
	public boolean hasShapeStocked() {
		return shapeStocked != null;
	}

	/**
	 * Verify if the Shape can be switched
	 * 
	 * @return True if the shape can be switched, False otherwise
	 */
	public boolean canSwitchShape() {
		return switchShape;
	}

	/**
	 * Change boolean to know if the Shape can be switched
	 * 
	 * @param b: boolean to know if the Shape can be switched
	 */
	public void canSwitchShape(boolean b) {
		switchShape = b;
	}

	/**
	 * Get the Shape stocked
	 * 
	 * @return the Shape stocked
	 */
	public Shape getStockShape() {
		return shapeStocked;
	}

	/**
	 * Stock the current Shape
	 * 
	 * @param cs: CurrentShape
	 */
	public void stockShape(CurrentShape cs) {
		if (shapeStocked == null) {
			shapeStocked = new Shape(cs, true);
			switchShape = false;
		}
		updateObserver(null);
	}

	/**
	 * Get the player's number
	 * 
	 * @return the player's number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Get the number of removed lines
	 * 
	 * @return the number of removed lines
	 */
	public int getNumLinesTotalRemoved() {
		return numLinesTotalRemoved;
	}

	/**
	 * get the Dictionary
	 * 
	 * @return the Dictionary
	 */
	public Dictionary getDico() {
		return dico;
	}

	/**
	 * Try if the CurrentShape collides the CurrentModifier
	 * 
	 * @param s: the CurrentShape
	 */
	private void tryModifierCollision(CurrentShape s) {
		CurrentModifier cm = getGrid().getCurrentModifier();
		if (cm != null && s.tryCollision(cm, s.getX(), s.getY()) && this.modifier != null) {
			this.modifier = new Modifier(cm);
			updateObserver(this.modifier);
			getGrid().setCurrentModifier(null);
		}
	}

	/**
	 * Bring down the CurrentShape in terms of a step
	 * if the CurrentShape can't go lower, it inscribe in the grid
	 * 
	 * @param step
	 */
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

	/**
	 * Try to move the CurrentShape on the left
	 */
	public void left() {
		CurrentShape s = getCurrentShape();
		if (s != null) {
			if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX() - 1, s.getY())) {
				s.move(s.getX() - 1, s.getY());
			}
			tryModifierCollision(s);
		}
	}

	/**
	 * Try to move the CurrentShape on the right
	 */
	public void right() {
		CurrentShape s = getCurrentShape();
		if (s != null) {
			if (!s.tryCollision(boardGame.getGrid().getTGrid(), s.getX() + 1, s.getY())) {
				s.move(s.getX() + 1, s.getY());
			}
			tryModifierCollision(s);
		}
	}

	/**
	 * try to drop down the CurrentShape
	 */
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

	/**
	 * Try to rotate the CurrentShape
	 */
	public void rotate() {
		CurrentShape s = getCurrentShape();
		if (s != null) {
			s.rotateLeft(boardGame.getGrid().getTGrid());
		}
	}

	/**
	 * Get player's BoardGame
	 * 
	 * @returnplayer's BoardGame
	 */
	public BoardGame getBoardGame() {
		return boardGame;
	}

	/**
	 * Get BoardGame's grid
	 * 
	 * @return BoardGame's grid
	 */
	public Grid getGrid() {
		return boardGame.getGrid();
	}

	/**
	 * Get CurrentShape
	 * 
	 * @return CurrentShape
	 */
	protected CurrentShape getCurrentShape() {
		return boardGame.getGrid().getCurrentShape();
	}

	/**
	 * Get player's score
	 * 
	 * @return player's score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Increase the score
	 * 
	 * @param add: the number of increase
	 */
	public void addToScore(int add) {
		score = score + add;
		//score += 1100;
		if ((int) score / 1000 >= level) {
			setLevelUp();
		}
		updateObserver(null);
	}

	/**
	 * Get the state of the game
	 * 
	 * @return the state of the game
	 */
	public InGameState getState() {
		return state;
	}

	/**
	 * Get the player's level
	 * 
	 * @return the player's level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Increase the player's level
	 */
	public void setLevelUp() {
		if (level < 10) {
			++level;
		}
		setNewSpeedFall(speedFallInit - level * 83);
		updateObserver(null);
	}

	/**
	 * Get the number of removed lines
	 * 
	 * @return the number of removed lines
	 */
	int getNbLines() {
		return numLinesTotalRemoved;
	}

	/**
	 * Get the speed of fall
	 * 
	 * @return the speed of fall
	 */
	public int getSpeedFall() {
		return speedFall;
	}

	/**
	 * Switch to anagram mode
	 * 
	 * @param b: boolean to know if it can switch
	 */
	public void switchToAnagram(boolean b) {
		wordFinish = b != true;
		state = b ? InGameState.ANAGRAMME : InGameState.TETRIS;
		updateObserver(null);
	}

	/**
	 * Know if the mode is anagram
	 * 
	 * @return True if mode anagram, False otherwise
	 */
	public boolean isAnagram() {
		return state == InGameState.ANAGRAMME;
	}

	/**
	 * Know if the mode is tetris
	 * 
	 * @return True if mode tetris, False otherwise
	 */
	public boolean isTetris() {
		return state == InGameState.TETRIS;
	}

	/**
	 * Know if the mode is worddle
	 * 
	 * @return True if mode worddle, False otherwise
	 */
	public boolean isWorddle() {
		return state == InGameState.WORDDLE;
	}

	/**
	 * Know if the game is finish
	 * 
	 * @return True if the game is finish, False otherwise
	 */
	public boolean isFinish() {
		return state == InGameState.FINISH;
	}

	/**
	 * Set the state of the game to finish
	 */
	public void finish() {
		state = InGameState.FINISH;
	}

	/**
	 * Add a char in a Stringbuilder
	 * 
	 * @param c: the char to add
	 */
	public void addNewChar(char c) {
		word.append(c);
		updateObserver(null);
	}

	/**
	 * Get the word of the StringBuilder
	 * 
	 * @return the word of the StringBuilder
	 */
	public String getWord() {
		return word.toString();
	}

	/**
	 * Know if the word is finish
	 * 
	 * @return True if the word is finish, False otherwise
	 */
	public boolean isWordFinished() {
		return wordFinish;
	}

	/**
	 * Delete the word of the StringBuilder
	 */
	void clearWord() {
		word.delete(0, word.length());
		wordFinish = false;
		updateObserver(null);
	}

	/**
	 * @see Observable
	 */
	@Override
	public void addObserver(Observer obs) {
		listObserver.add(obs);
	}

	/**
	 * @see Observable
	 */
	@Override
	public void updateObserver(Object args) {
		for (Observer obs : listObserver) {
			obs.update(this, args);
		}
	}

	/**
	 * @see Observable
	 */
	@Override
	public void delAllObserver() {
		listObserver = new ArrayList<>();
	}

	/**
	 * Verify if the word enter is a word of the dictionary
	 * and verify if the word enter is the best possible word
	 * 
	 * @param bestWord: the best possible word
	 */
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

	/**
	 * Go to anagram mode
	 */
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

	/**
	 * Switch the mode to tetris and change the score in terms of the number of removed lines
	 * 
	 * @param numLinesRemoved: the number of removed lines
	 */
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

	/**
	 * Finish the word
	 */
	public void setWordFinish() {
		wordFinish = true;
		updateObserver(null);
	}

	/**
	 * Switch to mode worddle
	 * 
	 * @param b: True if we go into mode worddle, False if we go out mode worddle
	 */
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

	/**
	 * Keep the currentShape in memory
	 */
	public void stockCurrentShape() {
		currentShapeStocked = boardGame.getGrid().getCurrentShape();
		boardGame.getGrid().setCurrentShape(null);
	}

	/**
	 * Go to worddle mode
	 */
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

	/**
	 * Finish worddle mode
	 */
	public void finishWorddle() {
		getGrid().finishWorddle(currentShapeStocked);
		clearWord();
		updateObserver(null);
		state = InGameState.TETRIS;
	}

	/**
	 * Lunch the timer to wait 30 seconds before the player can go to mode worddle
	 */
	public final void startTimerBeforeWorddle() {
		timerBeforeWorddle = new Timer();
		timerBeforeWorddle.schedule(new WorddleTimerTask((this)), 30000);
		t = System.nanoTime();
		setWorddle(false);
	}

	/**
	 * Know if the player can go to mode worddle
	 * 
	 * @return True if the player can go to mode worddle, False otherwise
	 */
	public boolean canWorddle() {
		return worddle;
	}

	/**
	 * 
	 * @param b: True if the player can go to the mode worddle, False otherwise
	 */
	public void setWorddle(boolean b) {
		worddle = b;
	}

	/**
	 * Get time
	 * 
	 * @return time
	 */
	public long getTime() {
		return System.nanoTime() - t;
	}

	/**
	 * set speed of fall
	 * 
	 * @param s: new speed of fall
	 */
	public void setNewSpeedFall(int s) {
		speedFall = s;
	}

	/**
	 * Get initial speed of fall
	 * 
	 * @return initial speed of fall
	 */
	public int getSpeedFallInit() {
		return speedFallInit;
	}

	/**
	 * Get Modifier
	 * 
	 * @return Modifier
	 */
	public Modifier getModifier() {
		return modifier;
	}

	/**
	 * Know if the player has a Modifier
	 * 
	 * @return True if the Modifier has a player, False otherwise
	 */
	public boolean hasModifier() {
		return modifier != null;
	}

	/**
	 * Active Modifier
	 */
	public void activeModifier() {
		modifier.active(this);
		modifier = null;
		updateObserver(modifier);
	}

	/**
	 * Shake the boardGame
	 * 
	 * @param offsetX
	 * @param offsetY
	 */
	public void shake(int offsetX, int offsetY) {
		boardGame.setOffset(offsetX, offsetY);
		int[] offset = new int[2];
		offset[0] = offsetX;
		offset[1] = offsetY;
		updateObserver(offset);
	}

	/**
	 * Stop all Timers
	 * @throws InterruptedException
	 */
	public void stopAllTimers() throws InterruptedException {
	}
}
