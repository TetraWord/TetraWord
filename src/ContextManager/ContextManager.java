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
  
  public void run(){
    graphicEngine.renderFrame();
  }
  
  public void defineGameMenu(){
    Window w = graphicEngine.getWindow();
    w.defineGameMenu();
    w.repaint();
  }
  
  public void definePlayersGame(int numPlayer){
    //Graphic
    Window w = graphicEngine.getWindow();
    w.defineNewBoardGame();
    w.repaint();
    
    //GameEngine
    for(int i = 0; i < numPlayer; ++i){
      gameEngine.addNewPlayer();
    } 
    Player[] players = gameEngine.getPlayers();
    for(int i = 0; i < numPlayer; ++i ){
      Thread t = new Thread(new RunPlayer(players[i]));
      t.start();
    }
  }
}
