package GraphicEngine;

import javax.swing.JFrame;

public class GraphicEngine extends JFrame{
  public static final int WINDOW_WIDTH = 500;
  public static final int WINDOW_HEIGHT = 800;
  private static final GraphicEngine INSTANCE = new GraphicEngine();
  private final JFrame window;
  
  //Singleton
  private GraphicEngine(){
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
  }
  
  public static GraphicEngine getInstance(){
    return INSTANCE;
  }
  
  public void init(){
    
  }
  
  public static void main( String[] args ){
    GraphicEngine engine = getInstance();
    
    
  }
}
