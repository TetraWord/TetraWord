
package GameEngine;

public enum GameState {
  NO_STATE( "No State" ),
  IN_MENU( "In Menu State" ),
  IN_GAME( "In Game State" );

  private String name = "";
  
  GameState( String name ) {
    this.name = name;
  }
  
  public String getStateName() {
    return name;
  }

}
