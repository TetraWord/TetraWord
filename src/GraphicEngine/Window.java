package GraphicEngine;

import GameEngine.BoardGame;
import static GraphicEngine.GraphicEngine.WINDOW_HEIGHT;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Container;
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
  
  public void defineOptionGameMenu(){
    Menu2D menu = new Menu2D();
    menu.defineOptionGameMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }
  
  public void defineDesignMenu(){
    DesignMenu2D menu = new DesignMenu2D();
    this.setContentPane(menu);
    this.setVisible(true);
  }
  
  public void defineNewBoardGame(BoardGame model){
    Container pan = getContentPane();
    BoardGame2D boardGame = new BoardGame2D(model);
    model.addObservateur(boardGame);
    pan.add( boardGame );

    this.setVisible(true);
    boardGame.requestFocusInWindow();
  }

	public void reloadApercuBackground(String item) {
		Container pan = getContentPane();
		if(pan instanceof DesignMenu2D){
			((DesignMenu2D)pan).loadApercus(item);
		}
	}

  public void clear() {
    getContentPane().removeAll();
  }

}
