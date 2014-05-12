package GameEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <b> DesignMenu is the logical part of the menu that permit to choose design
 * preference.</b>
 * <p>
 * The DesigneMenu contains:
 * <ul>
 * <li> The list of names of themes. </li>
 * <li> The list of pathnames corresponding to background image files. </li>
 * <li> The list of pathnames corresponding to bricks image files. </li>
 * </ul>
 * </p>
 *
 *
 */
public class DesignMenu {

  /**
   * The names of themes.
   *
   * @see DesignMenu#getNames()
   */
  private final String[] name;

  /**
   * The pathname of background image files.
   *
   * @see DesignMenu#getBackground(int i)
   */
  private final String[] background;

  /**
   * The pathname of bricks image files.
   *
   * @see DesignMenu#getBackground(int i)
   */
  private final String[][] bricks;

  /**
   * DesignMenu constructor.
   * <p>
   * Initialize attributes of the class by reading all design files
   * (.properties).
   * </p>
   *
   * @see DesignMenu#readFile(String file, int i)
   */
  public DesignMenu() {
    /*Read design conf files */
    File repertoire = new File("conf/Design/");
    String[] liste = repertoire.list();

    name = new String[liste.length];
    background = new String[liste.length];
    bricks = new String[liste.length][];

    for (int i = 0; i < liste.length; ++i) {

      if (liste[i].endsWith(".properties")) {
        /*For each files, save background*/
        readFile("conf/Design/" + liste[i], i);
      }
    }
  }

  /**
   * Read a design files and set attributes at the right place.
   *
   * @param file The pathname of a .propertie file design
   * @param i The ith design
   */
  private void readFile(String file, int i) {

    Properties prop = new Properties();
    InputStream input = null;
    /* Get infos from file */
    try {

      input = new FileInputStream(file);

      // load a properties file
      prop.load(input);

      name[i] = prop.getProperty("name");
      background[i] = prop.getProperty("background");
      String[] listeBrick = prop.getProperty("brick").split(",");
      bricks[i] = new String[listeBrick.length];
      for (int j = 0; j < listeBrick.length; ++j) {
        bricks[i][j] = listeBrick[j];
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Get the list of names of theme.
   *
   * @return List of names of themes
   * @see DesignMenu#name
   */
  public String[] getNames() {
    return name;
  }

  /**
   * Get the pathname corresponding to the background image file of the
   * designated theme.
   *
   * @param i Number corresponding to the chosen theme
   * @return Pathname of the background image file
   * @see DesignMenu#background
   */
  public String getBackground(int i) {
    return background[i];
  }

  /**
   * Get pathnames corresponding to bricks image files of the designated theme.
   *
   * @param i The indice of the designated theme
   * @return List of pathnames of bricks of the chosen theme
   * @see DesignMenu#bricks
   */
  public String[] getBricks(int i) {
    return bricks[i];
  }
}
