
import ContextManager.ContextManager;

public class Tetris extends Thread {

  private final ContextManager manager = ContextManager.getInstance();
  
  public Tetris(){}
  
  public void init(){
    manager.init();
  }
  
  public void run(){
    manager.run();
  }
  
  public static void main( String[] args ){
    Tetris game = new Tetris();
    game.init();
    game.start();
  }

}
