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

public class Hud2D extends JPanel implements Observer {

  private final Hud hub;
  private final String font;
  private final Color color;
  private final JLabel playerName;
  private final JLabel mode;
  private final JLabel level;
  private final JLabel score;
  private String wordInProgress;
  //private final JLabel word;
  private final JLabel nbLinesRemoved;
  private final JLabel timerTimeLeft;
  //private final JLabel timerBeforeWorddle;
  private final JProgressBar timerBeforeWorddle;
  private final JLabel messages;
  private final Timer tMessage;
	private Font f;
  private Shape2D nextShape;
  private Shape2D stockShape;
	private Image modifier;

  public Hud2D(final Hud hub, String font, Color color) {

    this.font = font;
    this.color = color;
    this.nextShape = new Shape2D(hub.getNextShape());

    this.setLayout(null);
    this.setSize(650, 889);
    this.setOpaque(false);
    this.setVisible(true);

    this.hub = hub;

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
    playerName = new JLabel(hub.getPlayerName(), CENTER);
    setJLabel(playerName, 480, 10, 150, 45);
    playerName.setFont(f);
    this.add(playerName);

    /*UI Mode*/
    String modeName = "Mode " + hub.getState().getStateName();
    mode = new JLabel(modeName, CENTER);
    setJLabel(mode, 69, 85, 351, 45);
    mode.setFont(new Font(font, Font.BOLD, 30));
    this.add(mode);

    /* UI Level */
    level = new JLabel(Integer.toString(hub.getLevel()), CENTER);
    setJLabel(level, 583, 805, 30, 23);
    this.add(level);

    /* UI Score */
    score = new JLabel(Integer.toString(hub.getScore()), CENTER);
    setJLabel(score, 520, 700, 70, 23);
    this.add(score);

    /* UI Lines */
    nbLinesRemoved = new JLabel(Integer.toString(hub.getNbLines()), CENTER);
    setJLabel(nbLinesRemoved, 583, 760, 30, 23);
    this.add(nbLinesRemoved);

    /* UI Timer time left */
    timerTimeLeft = new JLabel(Long.toString(hub.getTimeLeft()), CENTER);
    setJLabel(timerTimeLeft, 400, 510, 300, 23);
    this.add(timerTimeLeft);

    /* UI Timer worddle */
    timerBeforeWorddle = new JProgressBar(0, 100);
    timerBeforeWorddle.setValue(0);
    timerBeforeWorddle.setStringPainted(true);
    timerBeforeWorddle.setBounds(473, 602, 157, 23);
    timerBeforeWorddle.setFont(new Font(font, Font.BOLD, 18));
    this.add(timerBeforeWorddle);
		
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
    //Set to init speed the player after 5 seconds
    tMessage.schedule(new TimerTask() {
      @Override
      public void run() {
        String message = hub.getOlderMessage();
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

  private void setJLabel(JLabel jl, int x, int y, int sx, int sy) {
    jl.setForeground(color);
    jl.setBounds(x, y, sx, sy);
    jl.setFont(f);
  }

  public void draw(Graphics g,  int offsetX, int offsetY) {
    nextShape.draw(g, 515 + offsetX, 130 + offsetY, 0.7);
    if (stockShape != null) {
      stockShape.draw(g, 515 + offsetX, 290 + offsetY, 0.7);
    }
		if(modifier != null){
			g.drawImage(modifier,532 + offsetX, 432 + offsetY, 35, 35, this);
		}
  }

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
        this.stockShape = new Shape2D(hub.getStockShape());
      }

      if (hub.getTimeLeft() > 0) {
        timerTimeLeft.setText(Long.toString(hub.getTimeLeft()));
      } else {
        timerTimeLeft.setText("Fini");
      }

      if (hub.getTimeBeforeWorddle() > 0) {
        int value = 100 - hub.getTimeBeforeWorddle() * 50 / 15;
        timerBeforeWorddle.setValue(value);
        timerBeforeWorddle.setString(Integer.toString(value) + " %");
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
