
import GraphicEngine.GraphicEngine;
import GameEngine.GameEngine;

/*
* Chocolate spread
*/

public class ContextManager {
  	
	private final GameEngine gameEngine = GameEngine.getInstance();
	private final GraphicEngine graphicEngine = GraphicEngine.getInstance();
  
  public ContextManager(){
    
  }
  
  public void init(){
    graphicEngine.init();
    gameEngine.init();
  }
  
  public void start(){
    
  }
  
  public static void main( String[] args ){
    
  }
}
