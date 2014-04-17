package GameEngine;

public enum MenuState {

  MAIN("Main Menu"),
  GAME("Game Menu"),
  OPTION("Option Menu"),
  DESIGN("Design Menu"),
  DIFFICULTY("Difficulty Menu"),
  MULTI("MultiPlayers Menu");

  private String name = "";

  MenuState(String name) {
    this.name = name;
  }

  public String getStateName() {
    return name;
  }
}