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
 * <b> Window is the main window of the application. </b>
 * <p>
 * Window inherits from JFrame. </p>
 * <p>
 * The DesigneMenu2d contains:
 * <ul>
 * <li> Dimensions of the window. </li>
 * <li> The name of the window.
 * </li>
 * <li> A menu if necessary. </li>
 * <li> The list of BoardGame2D. </li>
 * <li> The list of OverBoard2d. </li>
 * </ul>
 *
 * </p>
 *
 * @see JFrame
 */
public class Window extends JFrame {

  /**
   * Dimensions of the window.
   *
   * @see Dimension
   */
  public static final Dimension size = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

  /**
   * The name of the Window.
   */
  public static final String title = "TetraWord";

  /**
   * A menu to display is necessary.
   */
  public static Menu2D menu;

  /**
   * *
   * List of BoardGame2D to display is necessary.
   *
   * @see BoardGame2D
   */
  public final ArrayList<BoardGame2D> boardGames = new ArrayList<>();

  /**
   * List of OverBoard2D to display is necessary.
   */
  public final ArrayList<OverBoard2D> overBoards = new ArrayList<>();

  /**
   * Window default constructor. Initialize main features of the JFrame for the
   * Window.
   */
  public Window() {

    //Window settings
    this.setTitle(title);
    this.setSize(size);
    this.setResizable(false);
    this.setLocationRelativeTo(null); //to center the window
    this.setDefaultCloseOperation(EXIT_ON_CLOSE); //to exit on the red cross

  }

  /**
   * Define menu as main menu.
   */
  public void defineMainMenu() {
    menu = new Menu2D();
    menu.defineMainMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  /**
   * Define menu as game menu.
   */
  public void defineGameMenu() {
    menu = new Menu2D();
    menu.defineGameMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  /**
   * Define menu as options menu.
   */
  public void defineOptionGameMenu() {
    menu = new Menu2D();
    menu.defineOptionGameMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  /**
   * Define menu as design menu.
   */
  public void defineDesignMenu() {
    menu = new DesignMenu2D();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  /**
   * Define menu as difficulty menu.
   */
  public void defineDifficultyMenu() {
    menu = new DifficultyMenu2D();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  /**
   * Define menu as multiplayers menu.
   */
  public void defineMultiPlayersMenu() {
    menu = new Menu2D();
    menu.defineMultiPlayersMenu();
    this.setContentPane(menu);
    this.setVisible(true);
  }

  /**
   * Create a new BoardGame2D from a model and add it to the Window.
   *
   * @param model Model for the new BoardGame2D
   * @see BoardGame
   */
  public void defineNewBoardGame(BoardGame model) {
    Container pan = getContentPane();
    BoardGame2D boardGame = new BoardGame2D(model);
    model.addObserver(boardGame);
    pan.add(boardGame);
    this.boardGames.add(boardGame);

    this.setVisible(true);
  }

  /**
   * Create an OverBoard2D to display controls of the player.
   *
   * @param numPlayer Identifying of the Player
   */
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

  /**
   * Define menu as pause menu.
   */
  public void definePauseMenu() {
    Container pan = getContentPane();
    menu = new Menu2D();
    menu.definePauseMenu();
    setContentPane(menu);
    this.setVisible(true);
  }

  /**
   * Detache pause menu and display BoardGame2D.
   */
  public void stopPauseMenu() {
    this.clear();
    Container pan = getContentPane();
    for (int i = 0; i < boardGames.size(); ++i) {
      pan.add(boardGames.get(i));
    }
    this.setVisible(true);
  }

  /**
   * Add player key listener of the player to the Window.
   */
  public void addWindowListener() {
    this.addKeyListener(ContextManager.getInstance().getPlayerListener(0));
    this.addKeyListener(ContextManager.getInstance().getPlayerListener(1));
    this.requestFocusInWindow();
  }

  /**
   * Ask to the DesignMenu2D to reload overviews for preferences.
   *
   * @param item New chosen theme
   */
  public void reloadOverviews(String item) {
    Container pan = getContentPane();
    if (pan instanceof DesignMenu2D) {
      ((DesignMenu2D) pan).loadOverviews(item);
    }
  }

  /**
   * Clear the Window by detaching all elements
   */
  public void clear() {
    getContentPane().removeAll();
  }

  /**
   * Resize the Window depending on its attributes.
   */
  public void resize() {
    this.setSize(size);
  }

  /**
   * Get the Menu2D of the Window.
   *
   * @return Menu2D of the Window
   */
  public Menu2D getMenu() {
    return menu;
  }

  /**
   * Get the list of OverBoards2D of the Window.
   *
   * @return The list of OverBoards2D
   */
  public ArrayList<OverBoard2D> getOverBoards() {
    return overBoards;
  }

}
