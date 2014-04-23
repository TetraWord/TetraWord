package GraphicEngine;

import GameEngine.Hub;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;

public class Hub2D extends JPanel implements Observer {

  private final Hub hub;
  private final String font;
  private final Color color;
  private final JLabel mode;
  private final JLabel level;
  private final JLabel score;
  private final JLabel word;
  private final JLabel nbLines;
  private final JLabel timerBeforeWorddle;
	private Shape2D nextShape;

  public Hub2D(Hub hub, String font, Color color) {

    this.font = font;
    this.color = color;
		this.nextShape = new Shape2D(hub.getNextShape());

    this.setLayout(null);
    this.setSize(650, 889);
    this.setOpaque(false);
    this.setVisible(true);

    this.hub = hub;

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

    /* UI Word anagramme/wordlle */
    word = new JLabel(hub.getWord(), CENTER);
    setJLabel(word, 70, 0, 367, 55);
		word.setVerticalTextPosition(CENTER);
		word.setHorizontalAlignment(CENTER);
    this.add(word);

    /* UI Lines */
    nbLines = new JLabel(Integer.toString(hub.getNbLines()), CENTER);
    setJLabel(nbLines, 583, 760, 30, 23);
    this.add(nbLines);
    
    /* UI Timer worddle */
    timerBeforeWorddle = new JLabel(hub.getTimeBeforeWorddle(), CENTER);
    setJLabel(timerBeforeWorddle, 400, 600, 300, 23);
    this.add(timerBeforeWorddle);
  }

  private void setJLabel(JLabel jl, int x, int y, int sx, int sy) {
    jl.setForeground(color);
    jl.setBounds(x, y, sx, sy);
    jl.setFont(new Font(font, Font.BOLD, 24));
  }
	
	
  public void draw(Graphics g) {	
			nextShape.draw(g, 515, 130, 0.7);
  }

  @Override
  public void update(Observable o, Object args) {
    if (o instanceof Hub) {
      String modeName = "Mode " + hub.getState().getStateName();
      mode.setText(modeName);
      level.setText(Integer.toString(hub.getLevel()));
      score.setText(Integer.toString(hub.getScore()));
			nbLines.setText(Integer.toString(hub.getNbLines()));
			
			String wordInProgress = hub.getWord();
			if(!wordInProgress.isEmpty()){
				System.out.println(wordInProgress);
				word.setText("Mot en cours : " + wordInProgress);
			}else{
				word.setText("");
			}
			
			this.nextShape = new Shape2D(hub.getNextShape());
			
      if(Integer.decode(hub.getTimeBeforeWorddle()) > 0){
        timerBeforeWorddle.setText(hub.getTimeBeforeWorddle());
      }else if(hub.getState().getStateName().compareTo("Worddle") != 0){
        timerBeforeWorddle.setText("Worddle ready !");
      }else{
        timerBeforeWorddle.setText("Worddle used !");
      }
    }
  }
}
