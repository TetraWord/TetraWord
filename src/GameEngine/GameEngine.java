package GameEngine;

public class GameEngine {

  private static final GameEngine INSTANCE = new GameEngine();
  
  //Singleton
  private GameEngine(){
    
  }
  
  public static GameEngine getInstance(){
    return INSTANCE;
  }
  
  public void init(){
    
  }
  
  public static void main( String[] args ){
  }

}
