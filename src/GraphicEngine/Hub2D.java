package GraphicEngine;

import GameEngine.Hub;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;

public class Hub2D extends JPanel implements Observer {

  private final Hub hub;
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
  private final JLabel timerBeforeWorddle;
  private final JLabel messages;
  private final Timer tMessage;
  private Shape2D nextShape;
  private Shape2D stockShape;

  public Hub2D(final Hub hub, String font, Color color) {

    this.font = font;
    this.color = color;
    this.nextShape = new Shape2D(hub.getNextShape());

    this.setLayout(null);
    this.setSize(650, 889);
    this.setOpaque(false);
    this.setVisible(true);

    this.hub = hub;

    /* UI Player name */
    playerName = new JLabel(hub.getPlayerName(), CENTER);
    setJLabel(playerName, 480, 10, 150, 45);
    playerName.setFont(new Font(font, Font.BOLD, 20));
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

    /* UI Word anagramme/wordlle */
    /*word = new JLabel(hub.getWord(), CENTER);
     setJLabel(word, 70, 0, 367, 55);
     word.setVerticalTextPosition(CENTER);
     word.setHorizontalAlignment(CENTER);
     this.add(word);*/

    /* UI messages */
    messages = new JLabel("", CENTER);
    setJLabel(messages, 70, 0, 367, 55);
    messages.setVerticalTextPosition(CENTER);
    messages.setHorizontalAlignment(CENTER);
    this.add(messages);
    
    /* UI Timer time left */
    timerTimeLeft = new JLabel(Long.toString(hub.getTimeLeft()), CENTER);
    setJLabel(timerTimeLeft, 400, 500, 300, 23);
    this.add(timerTimeLeft);

    /* UI Timer worddle */
    timerBeforeWorddle = new JLabel(Integer.toString(hub.getTimeBeforeWorddle()), CENTER);
    setJLabel(timerBeforeWorddle, 400, 600, 300, 23);
    this.add(timerBeforeWorddle);

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
    jl.setFont(new Font(font, Font.BOLD, 24));
  }

  public void draw(Graphics g) {
    nextShape.draw(g, 515, 130, 0.7);
    if (stockShape != null) {
      stockShape.draw(g, 515, 290, 0.7);
    }
  }

  @Override
  public void update(Observable o, Object args) {
    if (o instanceof Hub) {
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
        timerBeforeWorddle.setText(Integer.toString(hub.getTimeBeforeWorddle()));
      } else if (hub.getState().getStateName().compareTo("Worddle") != 0) {
        timerBeforeWorddle.setText("Worddle prêt !");
      } else {
        timerBeforeWorddle.setText("Worddle en cours !");
      }
    }
  }
}
