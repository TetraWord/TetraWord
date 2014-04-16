
package GameEngine;

public enum InGameState {
	TETRIS("Tetris"),
  ANAGRAMME("Anagramme"),
  WORDLE("Wordle");
	
	private String name = "";

  InGameState(String name) {
    this.name = name;
  }

  public String getStateName() {
    return name;
  }
}
