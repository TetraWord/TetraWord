package GameEngine;

/**
 * <b>Enumeration of states for the game</b>
 */
public enum InGameState {

	/**
	 * Tetris mode
	 */
  TETRIS("Tetris"),
	/**
	 * Anagram mode
	 */
  ANAGRAMME("Anagramme"),
	/**
	 * Worddle mode
	 */
  WORDDLE("Worddle"),
	/**
	 * Game finish
	 */
  FINISH("Finish");

	/**
	 * Name of the state
	 * @see InGameState#getStateName() 
	 */
  private String name = "";

	/**
	 * Dafault InGameState constructor
	 * @param name Name of the state
	 */
  InGameState(String name) {
    this.name = name;
  }

	/**
	 * Get the name of the enum
	 * @return Name of the enum
	 * 
	 * @see InGameState#name
	 */
  public String getStateName() {
    return name;
  }
}
