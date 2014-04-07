package GraphicEngine;

import GameEngine.BoardGame;
import GameEngine.Player;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;

/**
 * *
 * Define one board for a player with it's own grid
 */
public class BoardGame2D extends JPanel implements Observer {

  private Shape2D nextShape;
  private final Grid2D gameGrid;
  private final BoardGame model;
  private String background;
  private final JLabel level;

  public BoardGame2D(BoardGame model) {
    /* Settings */
    this.setLayout(null);
    this.setSize(650, 889);
    this.model = model;
    
    /* UI NextShape */
    nextShape = new Shape2D(model.getNextShape());
    
    /* UI Level */
    this.level = new JLabel("1", CENTER);
    setJLabel(level, 530, 770, 50, 50);
    this.add(level);

    gameGrid = new Grid2D(model.getGrid());
    model.getGrid().addObservateur(gameGrid);

    setShapeToGrid2D();
    
    /** Graphics properties **/
    Properties prop = new Properties();
    InputStream input = null;

    /*Get background image from file myConf*/
    try {
      input = new FileInputStream("conf/myConf.properties");

      // load a properties file
      prop.load(input);

      background = prop.getProperty("background");

    } catch (IOException ex) {
      /*Default background*/
      background = "media/Design/Paper/background.jpg";
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

  private void setJLabel(JLabel jl, int x, int y, int sx, int sy){
    jl.setBounds(x, y, sx, sy);
    jl.setFont(new Font("Arial", Font.BOLD, 22));
  }
  
  public void setLevelUp(String level) {
    this.level.setText(level);
  }
  
  private void setShapeToGrid2D() {
    gameGrid.setShape2D(model.getGrid().getCurrentShape());
  }

  @Override
  public void update(Observable o, Object args) {
    if(args == null){
      nextShape = new Shape2D(model.getNextShape());
    }
    if(args instanceof Integer){
      level.setText(String.valueOf(args));
    }
    
  }

  @Override
  public void paintComponent(Graphics g) {

    try {
      /*Try to load background image from chosen design*/

      Image img = ImageIO.read(new File(background));
      g.drawImage(img, 0, 0, this);
    } catch (IOException e) {
      /*Load background image from default design*/
      e.printStackTrace();
    }
    drawHUB(g);
    gameGrid.paintComponent(g);
  }

  private void drawHUB(Graphics g) {
    Player p = model.getPlayer();
    if (p.hasShapeStocked()) {
      drawShapeStocked(p, g);
    }
    drawNextShape(g);
  }

  private void drawNextShape(Graphics g) {
    nextShape.draw(g, 500, 150, 0.8);
  }

  private void drawShapeStocked(Player p, Graphics g) {
    //To do
  }

}
