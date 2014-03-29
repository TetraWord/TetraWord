package GraphicEngine;

public class GraphicEngine {
  
  
  public static final int WINDOW_WIDTH = 650;
  public static final int WINDOW_HEIGHT = 889;
  
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

  public void renderFrame() {
    window.repaint();
  }

}
