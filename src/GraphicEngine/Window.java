package GraphicEngine;

import ContextManager.ContextManager;
import GameEngine.BoardGame;
import static GraphicEngine.GraphicEngine.WINDOW_HEIGHT;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Dimension;
import javax.swing.JFrame;

/***
 * Define the main window of the application
 */

public class Window extends JFrame {
  
  public static final Dimension size = new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT);
  public static final String title = "TetraWord";

  public Window(){
    
    //Window settings
    this.setTitle(title);
    this.setSize(size);
    this.setResizable(false);
    this.setLocationRelativeTo(null); //to center the window
    this.setDefaultCloseOperation(EXIT_ON_CLOSE); //to exit on the red cross
  }
  
  public void defineMainMenu(){
    Menu2D menu = new Menu2D();
    menu.defineMainMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }
  
  public void defineGameMenu(){
    Menu2D menu = new Menu2D();
    menu.defineGameMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }
  
  public void defineNewBoardGame(BoardGame model){
    BoardGame2D boardGame = new BoardGame2D(model);
    this.setContentPane( boardGame );
    this.setVisible(true);
    boardGame.requestFocusInWindow();
    boardGame.start();
  }

  public void clear() {
    getContentPane().removeAll();
  }

}
