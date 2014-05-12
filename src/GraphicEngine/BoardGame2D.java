package GraphicEngine;

import GameEngine.BoardGame;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * <b> BoardGame2D represents one graphical board for a logical Player with it's
 * own graphical Grid2D and Hud2D. </b>
 * <p>
 * BoardGame2D is a JPanel swing's component. </p>
 * <p>
 * It is an Observer of the BoardGame, it's model and logical part. </p>
 * <p>
 * It contains: </p>
 * <ul>
 * <li> The Grid2D. </li>
 * <li> The Hud2D. </li>
 * <li> Its logical model. </li>
 * <li> A background image. </li>
 * <li> A font for text. </li>
 * <li> A color for text. </li>
 * <li> A boolean to know if Shape's shadow has to be display. </li>
 * </ul>
 *
 * @see Grid2D
 */
public class BoardGame2D extends JPanel implements Observer {

  private final Grid2D gameGrid;
  private final Hud2D hud;
  private final BoardGame model;
  private Image img;
  private String font;
  private Color color;
  private boolean shadow;

  /**
   * Constructor.
   * <p>
   * Set the size of the BoardGame2D, assign its model, load the different
   * options and design for the game.</p>
   * <p>
   * It also creates the Grid2D and the Hud2D, add and place them into the
   * BoardGame2D </p>
   *
   * @param model The BoardGame model.
   */
  public BoardGame2D(BoardGame model) {
    /* Settings */
    this.setLayout(null);
    this.setSize(650, 889);
    this.model = model;

    loadDesign();
    loadOptions();

    hud = new Hud2D(model.getHud(), font, color);
    model.getHud().addObserver(hud);
    this.add(hud);

    gameGrid = new Grid2D(model.getGrid(), shadow);
    gameGrid.setBounds(70, 135, 350, 700);

    model.getGrid().addObserver(gameGrid);
    this.add(gameGrid);
  }

  /**
   * Load the design wanted in the parameter with a .properties file.
   */
  private void loadDesign() {
    /**
     * Graphics properties *
     */
    Properties prop = new Properties();
    InputStream input = null;

    /*Get background image from file design*/
    String background;
    try {
      input = new FileInputStream("conf/design.properties");

      // load a properties file
      prop.load(input);

      background = prop.getProperty("background");
      font = prop.getProperty("font");
      String[] nbColor = prop.getProperty("color").split(",");
      color = new Color(Integer.parseInt(nbColor[0]), Integer.parseInt(nbColor[1]), Integer.parseInt(nbColor[2]));

    } catch (IOException ex) {
      /*Default background*/
      background = "media/Design/Futur/background.jpg";
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

    try {
      // Try to load background image from chosen design
      this.img = ImageIO.read(new File(background));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Load options wanted in parameters with a .properties file.
   */
  private void loadOptions() {
    Properties prop = new Properties();
    InputStream input = null;

    /*Get background image from file design*/
    try {
      input = new FileInputStream("conf/options.properties");

      // load a properties file
      prop.load(input);

      shadow = Boolean.parseBoolean(prop.getProperty("shadow"));

    } catch (IOException ex) {
      /*Default background*/
      shadow = false;
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
   * @see Observer
   */
  @Override
  public void update(Observable o, Object args) {
    if (args == null) {
      hud.update(o, args);
    }

  }

  /**
   * Override of paintComponent function to draw the background image, the Hud2D
   * and the Grid2D.
   *
   * @param g Graphics to draw on
   */
  @Override
  public void paintComponent(Graphics g) {

    int offsetX = model.getOffsetX();
    int offsetY = model.getOffsetY();
    g.drawImage(img, offsetX, offsetY, this);

    hud.draw(g, offsetX, offsetY);
    gameGrid.draw(g, offsetX, offsetY);
  }

}
