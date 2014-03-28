package ContextManager;


import GraphicEngine.*;
import GameEngine.*;

/*
* Chocolate spread
*/

public class ContextManager {
  
  private GameState lastState;
	private final GameEngine gameEngine = GameEngine.getInstance();
	private final GraphicEngine graphicEngine = GraphicEngine.getInstance();
  private static final ContextManager INSTANCE = new ContextManager();
  private static final MenuEventListener menuListener = new MenuEventListener();
  private static final PlayerEventListener gameListener = new PlayerEventListener();
  
  private ContextManager(){
    lastState = GameState.NO_STATE;
  }
  
  public static ContextManager getInstance(){
    return INSTANCE;
  }
  
  public static PlayerEventListener getGameListener(){
    return gameListener;
  }
  
  public static MenuEventListener getMenuListener(){
    return menuListener;
  }
   
  public void init(){
    graphicEngine.init();
    gameEngine.init();
  }
  
  public void start(){
    while(true){
      updateContentIfNeeded();
    }
  }
  
  public void updateContentIfNeeded(){
    GameState currentGameState = gameEngine.getState();
    if( currentGameState != lastState ){
      if ( currentGameState == GameState.IN_GAME){
        gameEngine.addNewPlayer();
      }
      System.out.println("I need updating");
      lastState = currentGameState;
    }
  }
}
