package GraphicEngine;

import ContextManager.ContextManager;
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
	public static Menu2D menu;

  public Window(){
    
    //Window settings
    this.setTitle(title);
    this.setSize(size);
    this.setResizable(false);
    this.setLocationRelativeTo(null); //to center the window
    this.setDefaultCloseOperation(EXIT_ON_CLOSE); //to exit on the red cross
  }
  
  public void defineMainMenu(){
    menu = new Menu2D();
    menu.defineMainMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }
  
  public void defineGameMenu(){
    menu = new Menu2D();
    menu.defineGameMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }
  
  public void defineOptionGameMenu(){
    menu = new Menu2D();
    menu.defineOptionGameMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }
  
  public void defineDesignMenu(){
    menu = new DesignMenu2D();
    this.setContentPane(menu);
    this.setVisible(true);
  }
  
  public void defineNewBoardGame(BoardGame model){
    Container pan = getContentPane();
    BoardGame2D boardGame = new BoardGame2D(model);
    model.addObservateur(boardGame);
    pan.add( boardGame );

    this.setVisible(true);
    this.addKeyListener(ContextManager.getInstance().getPlayerListener(0));
    this.addKeyListener(ContextManager.getInstance().getPlayerListener(1));
    this.requestFocusInWindow();
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
	
	public Menu2D getMenu(){
		return menu;
	}

}
