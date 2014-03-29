package ContextManager;


import GraphicEngine.*;
import GameEngine.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;

/*
* Chocolate spread
*/

public class ContextManager {
  
  private GameState lastState;
	private final GameEngine gameEngine = GameEngine.getInstance();
	private final GraphicEngine graphicEngine = GraphicEngine.getInstance();
  private static final ContextManager INSTANCE = new ContextManager();
  private static final MenuEventListener menuListener = new MenuEventListener();
  private static Config1 player1Listener = null;
  private static Config2 player2Listener = null;
  
  private ContextManager(){
    lastState = GameState.NO_STATE;
  }
  
  public static ContextManager getInstance(){
    return INSTANCE;
  }
  
  public KeyAdapter getPlayerListener(int index){
    if(index == 0){
      return player1Listener;
    }
    if(index == 1){
      return player2Listener;
    }
    return null;
  }
  
  public static MenuEventListener getMenuListener(){
    return menuListener;
  }
   
  public void init(){
    graphicEngine.init();
    gameEngine.init();
  }
  
  public void update(){
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
    w.clear();
    w.setLocationRelativeTo(null);
    
    if(numPlayer > 1){
      Dimension size = w.getSize();
      size.width *= 2;
      w.setSize(size);
      w.setLayout(new GridLayout(1,2));
    }
    
    for(int i = 0; i < numPlayer; ++i){
      gameEngine.addNewPlayer();
    }
    
    Player[] players = gameEngine.getPlayers();
    for(int i = 0; i < numPlayer; ++i ){
      if(i == 0){
        player1Listener = new Config1(players[i]);
      }
      if(i == 1){
        player2Listener = new Config2(players[i]);
      }
      w.defineNewBoardGame(players[i].getBoardGame());
 
      Thread t = new RunPlayer(players[i]);
      t.start();
    }
    
    w.repaint();
    
  }
}
