package GraphicEngine;

import ContextManager.ContextManager;
import GameEngine.BoardGame;
import static GraphicEngine.GraphicEngine.WINDOW_HEIGHT;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * *
 * Define the main window of the application
 */
public class Window extends JFrame {

  public static final Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
  public static final String title = "TetraWord";
  public static Menu2D menu;
  public final ArrayList<BoardGame2D> boardGames = new ArrayList<>();
  public final ArrayList<OverBoard2D> overBoards = new ArrayList<>();

  public Window() {

    //Window settings
    this.setTitle(title);
    this.setSize(size);
    this.setResizable(false);
    this.setLocationRelativeTo(null); //to center the window
    this.setDefaultCloseOperation(EXIT_ON_CLOSE); //to exit on the red cross

  }

  public void defineMainMenu() {
    menu = new Menu2D();
    menu.defineMainMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  public void defineGameMenu() {
    menu = new Menu2D();
    menu.defineGameMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  public void defineOptionGameMenu() {
    menu = new Menu2D();
    menu.defineOptionGameMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  public void defineDesignMenu() {
    menu = new DesignMenu2D();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  public void defineDifficultyMenu() {
    menu = new DifficultyMenu2D();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  public void defineMultiPlayersMenu() {
    menu = new Menu2D();
    menu.defineMultiPlayersMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  public void defineNewBoardGame(BoardGame model) {
    Container pan = getContentPane();
    BoardGame2D boardGame = new BoardGame2D(model);
    model.addObservateur(boardGame);
    pan.add(boardGame);
    this.boardGames.add(boardGame);

    this.setVisible(true);
  }

  public void defineCommande(int numPlayer) {
		try {
			Container pan = getContentPane();
			OverBoard2D overBoard = new OverBoard2D(numPlayer);
			pan.add(overBoard);
			this.overBoards.add(overBoard);
			this.setVisible(true);
		} catch (IOException ex) {
			Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
		}
  }
  
  public void definePauseMenu() {
    Container pan = getContentPane();
    menu = new Menu2D();
    menu.definePauseMenu();
    setContentPane(menu);
    this.setVisible(true);
  }

  public void stopPauseMenu() {
    this.clear();
    Container pan = getContentPane();
    for(int i = 0; i < boardGames.size(); ++i){
      pan.add(boardGames.get(i));
    }
    this.setVisible(true);
  }
  
  public void addWindowListener() {
    this.addKeyListener(ContextManager.getInstance().getPlayerListener(0));
    this.addKeyListener(ContextManager.getInstance().getPlayerListener(1));
    this.requestFocusInWindow();
  }

  public void reloadApercuBackground(String item) {
    Container pan = getContentPane();
    if (pan instanceof DesignMenu2D) {
      ((DesignMenu2D) pan).loadApercus(item);
    }
  }

  public void clear() {
    getContentPane().removeAll();
  }

  public Menu2D getMenu() {
    return menu;
  }
  
  public ArrayList<OverBoard2D> getOverBoards() {
    return overBoards;
  }

}
