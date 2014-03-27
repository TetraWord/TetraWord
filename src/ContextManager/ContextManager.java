package ContextManager;


import GraphicEngine.*;
import GameEngine.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/*
* Chocolate spread
*/

public class ContextManager {
  
  private GameState lastState;
	private final GameEngine gameEngine = GameEngine.getInstance();
	private final GraphicEngine graphicEngine = GraphicEngine.getInstance();
  private static final ContextManager INSTANCE = new ContextManager();
  private static final MenuEventListener menuListener = new MenuEventListener();
  private static final GameEventListener gameListener = new GameEventListener();
  
  private ContextManager(){
    
  }
  
  public static ContextManager getInstance(){
    return INSTANCE;
  }
  
  public static GameEventListener getGameListener(){
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
    
  }
  
  public void updateContentIfNeeded(){
    GameState currentGameState = gameEngine.getState();
    if( currentGameState != lastState ){
      //We need to update the game
    }
  }
}
