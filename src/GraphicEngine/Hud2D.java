package GraphicEngine;

import GameEngine.Hud;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import static javax.swing.SwingConstants.CENTER;

/**
 * <b> Hud2D is the graphic representation of the hud that permits to show informations to the player </b>
 * <p> Hud2D inherits from JPanel</p>
 * <p>
 * The Hud2D contains :
 * <ul>
 * <li> A Hud model </li>
 * <li> A Color for default color of the texts </li>
 * <li> A JLabel containing the player's name </li>
 * <li> A JLabel containing the playing mode in progress </li>
 * <li> A JLabel containing the current level of the player </li>
 * <li> A JLabel containing the current score of the player </li>
 * <li> A String containing the word in progres for Worddle et anagram mode </li>
 * <li> A JLabel containing the number of removed lines </li>
 * <li> A JLabel containing the timer of the game </li>
 * <li> A progress bar for the time before the player can switch modes </li>
 * <li> A JLabel containing a message for the player </li>
 * <li> A timer to schedule apparition of messages </li>
 * <li> A Font for default font of the texts </li>
 * <li> The next shape the player will launch </li>
 * <li> The stocked shape of the player </li>
 * <li> An image of the stocked modifier </li>
 * </ul>
 * 
 * </p>
 *
 * @see Hud
 * @see JPanel
 * @see Observer
 */
public class Hud2D extends JPanel implements Observer {

	/**
	 * The Hub model
	 * @see Hud
	 */
  private final Hud hub;
	
	/**
	 * Color for texts
	 * @see Color
	 */
  private final Color color;
	
	/**
	 * The player's name
	 */
  private final JLabel playerName;
	
	/**
	 * Game mode in progress
	 */
  private final JLabel mode;
	
	/**
	 * Level of the player
	 */
  private final JLabel level;
	
	/**
	 * Score of the player
	 */
  private final JLabel score;
	
	/**
	 * Word in progress for Worddle et anagram mode
	 */
  private String wordInProgress;
	
	/**
	 * Number of removed lines
	 */
  private final JLabel nbLinesRemoved;
	
	/**
	 * Timer of left time
	 */
  private final JLabel timerTimeLeft;
	
	/**
	 * Timer before player can change mode
	 * @see JProgressBar
	 */
  private final JProgressBar timerBeforeWorddle;
	
	/**
	 * Message to show in the hud
	 */
  private final JLabel messages;
	
	/**
	 * Timer to schedule apparition of messages
	 */
  private final Timer tMessage;
	
	/**
	 * Font for texts
	 * @see Font
	 */
	private Font f;
	
	/**
	 * Next shape hold by the player
	 * @see Shape2D
	 */
  private Shape2D nextShape;
	
	/**
	 * Shape stocked by the player
	 * @see Shape2D
	 */
  private Shape2D stockedShape;
	
	/**
	 * Modifier hold by the player
	 * @see Image
	 */
	private Image modifier;

	/**
	 * Hud2D conctructor
	 * Create a Hud2D by initializa all attribute 
	 * 
	 * @param hud The Hud model
	 * @param font The default font for texts
	 * @param color The default color for texts
	 */
  public Hud2D(final Hud hud, String font, Color color) {

    this.color = color;
    this.nextShape = new Shape2D(hud.getNextShape());

    this.setLayout(null);
    this.setSize(650, 889);
    this.setOpaque(false);
    this.setVisible(true);

    this.hub = hud;

		/* Create font from font file */
		try {
			File fis = new File("media/font/" + font);
			f = Font.createFont(Font.TRUETYPE_FONT, fis);
			f = f.deriveFont((float) 20.0);
		} catch (FontFormatException ex) {
			Logger.getLogger(Hud2D.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Hud2D.class.getName()).log(Level.SEVERE, null, ex);
		}

    /* UI Player name */
    playerName = new JLabel(hud.getPlayerName(), CENTER);
    setJLabel(playerName, 480, 10, 150, 45);
    playerName.setFont(f);
    this.add(playerName);

    /*UI Mode*/
    String modeName = "Mode " + hud.getState().getStateName();
    mode = new JLabel(modeName, CENTER);
    setJLabel(mode, 69, 85, 351, 45);
		f.deriveFont((float) 30.0);
    mode.setFont(f);
		f.deriveFont((float) 20.0);
    this.add(mode);

    /* UI Level */
    level = new JLabel(Integer.toString(hud.getLevel()), CENTER);
    setJLabel(level, 583, 805, 30, 23);
    this.add(level);

    /* UI Score */
    score = new JLabel(Integer.toString(hud.getScore()), CENTER);
    setJLabel(score, 520, 700, 70, 23);
    this.add(score);

    /* UI Lines */
    nbLinesRemoved = new JLabel(Integer.toString(hud.getNbLines()), CENTER);
    setJLabel(nbLinesRemoved, 583, 760, 30, 23);
    this.add(nbLinesRemoved);

    /* UI Timer time left */
    timerTimeLeft = new JLabel(Long.toString(hud.getTimeLeft()), CENTER);
    setJLabel(timerTimeLeft, 400, 510, 300, 23);
    this.add(timerTimeLeft);

    /* UI Timer worddle */
    timerBeforeWorddle = new JProgressBar(0, 100);
    timerBeforeWorddle.setValue(0);
    timerBeforeWorddle.setStringPainted(true);
    timerBeforeWorddle.setBounds(473, 602, 157, 23);
		f.deriveFont((float) 18.0);
    timerBeforeWorddle.setFont(f);
		f.deriveFont((float) 20.0);
    this.add(timerBeforeWorddle);
		
		/* Create font from font file */
		try {
			File fis = new File("media/font/cinnamon cake.ttf");
			f = Font.createFont(Font.TRUETYPE_FONT, fis);
			f = f.deriveFont((float) 20.0);
		} catch (FontFormatException ex) {
			Logger.getLogger(Hud2D.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Hud2D.class.getName()).log(Level.SEVERE, null, ex);
		}
		
    /* UI messages */
    messages = new JLabel("", CENTER);
    setJLabel(messages, 70, 0, 367, 55);
    messages.setVerticalTextPosition(CENTER);
    messages.setHorizontalAlignment(CENTER);
		messages.setForeground(Color.BLACK);
    this.add(messages);

    tMessage = new Timer();
    /* Schedule apparition of messages every 2 seconds */
    tMessage.schedule(new TimerTask() {
      @Override
      public void run() {
        String message = hud.getOlderMessage();
        if (message != null) {
          messages.setText(message);
        } else {
          if (wordInProgress != null) {
            if (!wordInProgress.isEmpty()) {
              messages.setText("Mot en cours : " + wordInProgress);
            }
          }
        }
      }
    }, 0, 2000);
  }

	/**
	 * Set some parameter of Jlabel to standarize design
	 * @param jl The JLabel to standarize
	 * @param x Horizontal position
	 * @param y Vertical position
	 * @param sx Width of the element
	 * @param sy Height of the element
	 */
  private void setJLabel(JLabel jl, int x, int y, int sx, int sy) {
    jl.setForeground(color);
    jl.setBounds(x, y, sx, sy);
    jl.setFont(f);
  }

	/**
	 * Draw images of the hub (next shape and stocked shape)
	 * @param g Graphics to draw on
	 * @param offsetX Horizontal gap from the initial position (for shake modifier)
	 * @param offsetY Vertical gap from the initial position (for shake modifier)
	 */
  public void draw(Graphics g,  int offsetX, int offsetY) {
    nextShape.draw(g, 515 + offsetX, 130 + offsetY, 0.7);
    if (stockedShape != null) {
      stockedShape.draw(g, 515 + offsetX, 290 + offsetY, 0.7);
    }
		if(modifier != null){
			g.drawImage(modifier,532 + offsetX, 432 + offsetY, 35, 35, this);
		}
  }

	/**
	 * Update Hub2D attributes
	 * 
	 * @param o Observable which have notify
	 * @param args Arguments
	 */
  @Override
  public void update(Observable o, Object args) {
    if (o instanceof Hud) {
      playerName.setText(hub.getPlayerName());
      String modeName = "Mode " + hub.getState().getStateName();
      mode.setText(modeName);
      level.setText(Integer.toString(hub.getLevel()));
      score.setText(Integer.toString(hub.getScore()));
      nbLinesRemoved.setText(Integer.toString(hub.getNbLines()));

      wordInProgress = hub.getWord();
      if (wordInProgress != null) {
        if (!wordInProgress.isEmpty()) {
          messages.setText("Mot en cours : " + wordInProgress);
        }
      }

      this.nextShape = new Shape2D(hub.getNextShape());

      if (hub.getStockShape() != null) {
        this.stockedShape = new Shape2D(hub.getStockShape());
      }

      if (hub.getTimeLeft() > 0) {
        timerTimeLeft.setText(Long.toString(hub.getTimeLeft()));
      } else {
        timerTimeLeft.setText("Fini");
      }

      if (hub.getTimeBeforeWorddle() > 0) {
        timerBeforeWorddle.setValue(100 - hub.getTimeBeforeWorddle() * 50 / 15);
      } else if (hub.getState().getStateName().compareTo("Worddle") != 0) {
        timerBeforeWorddle.setValue(100);
        timerBeforeWorddle.setString("Worddle prêt !");
      } else {
        timerBeforeWorddle.setValue(0);
        timerBeforeWorddle.setString("Worddle en cours !");
      }
			
			if (hub.getModifier() != null ){
				String m = hub.getModifier().getName();
				String file = "./media/Design/modifieurs/" + m + ".png";
				try {
					modifier = ImageIO.read(new File(file));
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}else{
				modifier = null;
			}
			
			/* Shake modifier*/
			if (args != null ){
				if (args instanceof int[]){
					int offsetX = ((int[])(args))[0];
					int offsetY = ((int[])(args))[1];
					timerBeforeWorddle.setBounds(473 + offsetX, 602 + offsetY, 157, 23);
					setJLabel(playerName, 480 + offsetX, 10 + offsetY, 150, 45);
					setJLabel(mode, 69 + offsetX, 85 + offsetY, 351, 45);
					setJLabel(level, 583 + offsetX, 805 + offsetY, 30, 23);
					setJLabel(score, 520 + offsetX, 700 + offsetY, 70, 23);
					setJLabel(nbLinesRemoved, 583 + offsetX, 760 + offsetY, 30, 23);
					setJLabel(messages, 70 + offsetX, 0, 367 + offsetY, 55);
					setJLabel(timerTimeLeft, 400 + offsetX, 510 + offsetY, 300, 23);
				}
			}
    }
  }
}
