package GraphicEngine;

import ContextManager.ContextManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * <b> GraphicEngine is the graphical engine of the game.</b>.
 * <p>
 * It's a singleton class, so it can be called everywhere with its <b>
 * getInstance() </b> method. </p>
 * <p>
 * It's the main graphical part of the game. </p>
 * <p>
 * It contains the window of the game. </p>
 *
 * @see Window
 */
public class GraphicEngine {

  public static final int WINDOW_WIDTH = 650;
  public static final int WINDOW_HEIGHT = 889;

  /**
   * Instance of the GraphicEngine. Singleton.
   */
  private static final GraphicEngine INSTANCE = new GraphicEngine();
  private final Window window;

  /**
   * Constructor. Singleton.
   */
  private GraphicEngine() {
    window = new Window();
  }

  /**
   * Get the instance of the GraphicEngine. Singleton.
   *
   * @return The GraphicEngine
   */
  public static GraphicEngine getInstance() {
    return INSTANCE;
  }

  /**
   * Get the Window.
   *
   * @return The Window
   */
  public Window getWindow() {
    return window;
  }

  /**
   * Init the GraphicEngine at the start of the game.
   * <p>
   * Init the Window and create a design.properties file if it doesn't exist.
   * </p>
   */
  public void init() {
    window.defineMainMenu();

    File f = new File("conf/design.properties");
    if (!f.exists()) {
      Properties prop = new Properties();
      OutputStream output = null;

      try {

        output = new FileOutputStream("conf/design.properties");

        // set the properties value
        prop.setProperty("background", "media/Design/futur/background.jpg");
        prop.setProperty("brick", "media/Design/futur/brick.png");
        prop.setProperty("modifier", "media/Design/futur/modifier.png");
        prop.setProperty("font", "Merkur");
        prop.setProperty("color", "255,255,255");

        // save properties to project root folder
        prop.store(output, null);

      } catch (IOException io) {
        io.printStackTrace();
      } finally {
        if (output != null) {
          try {
            output.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }
    }
  }

  /**
   * Repaint the Window.
   */
  public void renderFrame() {
    window.repaint();
  }

  /**
   * Stop the graphical and event part of the game. Remove all the event
   * listener. Reinit the Window.
   */
  public void stop() {
    //remove listener on window
    window.removeKeyListener(ContextManager.getInstance().getPlayerListener(0));
    window.removeKeyListener(ContextManager.getInstance().getPlayerListener(1));
    window.clear();
    window.resize();
    window.setLocationRelativeTo(null);
    window.defineMainMenu();
  }

}
