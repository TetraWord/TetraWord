package GraphicEngine;

import javax.swing.JFrame;

public class GraphicEngine extends JFrame{
  public static final int WINDOW_WIDTH = 300;
  public static final int WINDOW_HEIGHT = 500;
  private static GraphicEngine INSTANCE = new GraphicEngine();
  
  //Singleton
  private GraphicEngine(){
    Window window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
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
