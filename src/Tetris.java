
public class Tetris {

  private final ContextManager manager = new ContextManager();
  
  public Tetris(){
   
  }
  
  public void init(){
    manager.init();
  }
  
  public void start(){
    
  }
  
  public static void main( String[] args ){
    Tetris game = new Tetris();
    game.init();
    game.start();
  }
}
