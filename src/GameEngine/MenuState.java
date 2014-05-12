package GameEngine;

/**
 * <b>Enumeration of states for the menu. </b>
 *
 */
public enum MenuState {

  /**
   * Main menu.
   */
  MAIN("Main Menu"),
  /**
   * Game menu.
   */
  GAME("Game Menu"),
  /**
   * Options menu.
   */
  OPTION("Option Menu"),
  /**
   * Design preferences menu.
   */
  DESIGN("Design Menu"),
  /**
   * Difficulty preferences menu.
   */
  DIFFICULTY("Difficulty Menu"),
  /**
   * Multiplayer menu.
   */
  MULTI("MultiPlayers Menu"),
  /**
   * Menu before starting game.
   */
  BEGIN("Begin Menu");

  /**
   * Name of the enumeration.
   *
   * @see MenuState#getStateName()
   */
  private String name = "";

  /**
   * MenuState constructor.
   *
   * @param name Name of the MenuState
   */
  MenuState(String name) {
    this.name = name;
  }

  /**
   * Get the name of the MenuState.
   *
   * @return Name of the MenuState
   * @see MenuState#name
   */
  public String getStateName() {
    return name;
  }
}
