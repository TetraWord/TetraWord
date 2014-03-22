package GraphicEngine;

public class GraphicEngine {
  public static final int WINDOW_WIDTH = 500;
  public static final int WINDOW_HEIGHT = 800;
  private static final GraphicEngine INSTANCE = new GraphicEngine();
  private final Window window;
  
  //Singleton
  private GraphicEngine(){
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
  }
  
  public static GraphicEngine getInstance(){
    return INSTANCE;
  }
  
  public void init(){
    window.defineMainMenu();
  }
  
  public Window getWindow(){
    return window;
  }
  
  public static void main( String[] args ){
    GraphicEngine engine = getInstance();
    engine.window.defineNewBoardGame();
    
  }
}
