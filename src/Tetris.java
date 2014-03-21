
public class Tetris {

  private final ContextManager manager;
  
  public Tetris(){
     manager = new ContextManager();
  }
  
  public void init(){
    manager.init();
  }
  
  public void start(){
    manager.start();
  }
  
  public static void main( String[] args ){
    Tetris game = new Tetris();
    game.init();
    game.start();
  }
}
