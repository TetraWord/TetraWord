package GraphicEngine;

import GameEngine.Hub;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Color;
import java.awt.Font;
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

  public Hub2D(Hub hub, String font, Color color) {

    this.font = font;
    this.color = color;
    System.out.println(font);

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
    setJLabel(level, 516, 786, 57, 23);
    this.add(level);

    /* UI Score */
    score = new JLabel(Integer.toString(hub.getScore()), CENTER);
    setJLabel(score, 520, 690, 57, 23);
    this.add(score);
  }

  private void setJLabel(JLabel jl, int x, int y, int sx, int sy) {
    jl.setForeground(color);
    jl.setBounds(x, y, sx, sy);
    jl.setFont(new Font(font, Font.BOLD, 22));
  }

  /*public void setLevelUp(String level) {
   this.level.setText(level);
   }*/
  @Override
  public void update(Observable o, Object args) {
    if (o instanceof Hub) {
      String modeName = "Mode " + hub.getState().getStateName();
      mode.setText(modeName);
      level.setText(Integer.toString(hub.getLevel()));
      score.setText(Integer.toString(hub.getScore()));
    }
  }

}
