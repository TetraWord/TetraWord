package GameEngine;

public class GameEngine {

  private GameState currentState;
  private static final GameEngine INSTANCE = new GameEngine();
  
  //Singleton
  private GameEngine(){
    currentState = GameState.NO_STATE;
  }
  
  public static GameEngine getInstance(){
    return INSTANCE;
  }
  
  public void init(){
    currentState = GameState.IN_MENU;
  }
  
  public void update(){
    
  }

  public GameState getState() {
    return currentState;
  }

}