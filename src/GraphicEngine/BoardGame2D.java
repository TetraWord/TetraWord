package GraphicEngine;

import ContextManager.GridEventListener;
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
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * *
 * Define one board for a player with it's own grid
 */
public class BoardGame2D extends JPanel implements Observer {

  private final Grid2D gameGrid;
  private final BoardGame model;
  private Image img;
  private String font;
  private Color color;
  private final GridEventListener event;
  private boolean shadow;
	private final Hub2D hub;

  public BoardGame2D(BoardGame model) {
    /* Settings */
    this.setLayout(null);
    this.setSize(650, 889);
    this.model = model;

    loadDesign();
    loadOptions();

    hub = new Hub2D(model.getHub(), font, color);
    model.getHub().addObservateur(hub);
    this.add(hub);

    event = new GridEventListener(this.model);
    gameGrid = new Grid2D(model.getGrid(), event, shadow);
    gameGrid.setBounds(70, 135, 350, 700);

    model.getGrid().addObservateur(gameGrid);
    this.add(gameGrid);

    setShapeToGrid2D();
    setModifierToGrid();
  }

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

  private void setShapeToGrid2D() {
    gameGrid.setShape2D(model.getGrid().getCurrentShape());
  }
  
  private void setModifierToGrid() {
  	gameGrid.setModifier2D(model.getGrid().getCurrentModifier());
  }

  @Override
  public void update(Observable o, Object args) {
    if (args == null) {
      hub.update(o, args);
    }

  }

  @Override
  public void paintComponent(Graphics g) {
		
		int offsetX = model.getOffsetX();
		int offsetY =  model.getOffsetY();
    g.drawImage(img, offsetX, offsetY, this);
		
		hub.draw(g, offsetX, offsetY);
    gameGrid.draw(g, offsetX, offsetY);
  }

}
