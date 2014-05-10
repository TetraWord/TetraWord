package GraphicEngine;

import ContextManager.ContextManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class GraphicEngine {

  public static final int WINDOW_WIDTH = 650;
  public static final int WINDOW_HEIGHT = 889;

  private static final GraphicEngine INSTANCE = new GraphicEngine();
  private final Window window;

  //Singleton
  private GraphicEngine() {
    window = new Window();
  }

  public static GraphicEngine getInstance() {
    return INSTANCE;
  }

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

  public Window getWindow() {
    return window;
  }

  public void renderFrame() {
    window.repaint();
  }

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
