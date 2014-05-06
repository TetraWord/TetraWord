
import ContextManager.ContextManager;

/**
 * <b> Tetris is the class representing the game. </b>
 * <p>
 * The Tetris class extends from Thread, so it has a <b> run </b> method to be
 * launch.
 * </p>
 * <p>
 * A Tetris game is composed of a manager that must be init from the method <b>
 * init</b>.
 * </p>
 *
 * @see ContextManager
 */
public class Tetris extends Thread {

  /**
   * The manager of the game. It is not modifiable.
   *
   * @see ContextManager#getInstance()
   */
  private final ContextManager manager = ContextManager.getInstance();

  /**
   * Default constructor.
   */
  public Tetris() {
  }

  /**
   * Initialize the manager of the game.
   *
   * @see ContextManager#init()
   */
  public void init() {
    manager.init();
  }

  /**
   * Launch the game when call the method start on it.
   * <p>
   * It contains the loop game.
   * </p>
   *
   * @see ContextManager#update()
   */
  @Override
  public void run() {
    while (true) {
      manager.update();
    }
  }

  /**
   * <b> Main method of the game </b>
   * <p>
   * This main method create the game, initialize and start it </p>
   *
   * @param args
   */
  public static void main(String[] args) {
    Tetris game = new Tetris();
    game.init();
    game.start();
  }

}
