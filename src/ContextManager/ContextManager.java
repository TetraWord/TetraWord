package ContextManager;


import GraphicEngine.*;
import GameEngine.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/*
* Chocolate spread
*/

public class ContextManager implements ActionListener {
  
  private GameState lastState;
	private final GameEngine gameEngine = GameEngine.getInstance();
	private final GraphicEngine graphicEngine = GraphicEngine.getInstance();
  private static final ContextManager INSTANCE = new ContextManager();
  private final MenuEventListener menuListener = new MenuEventListener();
  private final GameEventListener gameListener = new GameEventListener();
  
  private ContextManager(){
    
  }
  
  public static ContextManager getInstance(){
    return INSTANCE;
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

  //Peut être qu'une optimisation peut être faite ici notamment en ne passant pas par cette classe
  @Override
  public void actionPerformed(ActionEvent e) {
    JPanel pan = graphicEngine.getWindow().getPan();
    if( pan instanceof Menu2D ){
      menuListener.actionPerformed(e);
    }
    else if( pan instanceof BoardGame2D){
	  pan.addKeyListener(gameListener);	
      gameListener.actionPerformed(e);
      
    }
    
  }
}
