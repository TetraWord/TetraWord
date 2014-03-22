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

  @Override
  public void actionPerformed(ActionEvent e) {
    JPanel pan = graphicEngine.getWindow().getPan();
    if( pan instanceof Menu2D ){ 
      Menu2D menu = (Menu2D)pan;
      Button2D[] buttons = menu.getButtons();
      for( int i = 0; i < buttons.length; ++i){
        if( e.getSource() == buttons[i]){
          System.out.println("Clic sur le bouton "+buttons[i].getName()+" ! ");
        }
      }
    }
    else if( pan instanceof BoardGame2D){
      System.out.println("Clic sur un element du jeu");
    }
    
  }
}
