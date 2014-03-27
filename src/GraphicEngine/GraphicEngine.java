package GraphicEngine;

public class GraphicEngine {
  
  
  public static final int WINDOW_WIDTH = 500;
  public static final int WINDOW_HEIGHT = 800;
  
  private static final GraphicEngine INSTANCE = new GraphicEngine();
  private final Window window;
  
  //Singleton
  private GraphicEngine(){
    window = new Window();
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
}
