package GraphicEngine;

import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <b>Screen before the begin of the game. </b>
 * <p>
 * OverBoards2D inherits from JPanel. </p>
 * <p>
 * OverBoards2D contains:
 * <ul>
 * <li>Image of controls. </li>
 * <li>JTextField for the player's name. </li>
 * <li>Play button. </li>
 * </ul>
 * </p>
 */
public class OverBoard2D extends JPanel {

  /**
   * Image of controls.
   */
  private final Image img;

  /**
   * JTextField for the Player's name.
   */
  private final JTextField namePlayer = new JTextField("Pseudo");

  /**
   * Play button.
   */
  private static final Button2D play = new Button2D("Jouer", WINDOW_WIDTH / 2 - 150, 780, 300, 50);

  /**
   * Default OverBoard2D constructor.
   *
   * @param numPlayer Identifying of the Player
   * @throws IOException
   */
  public OverBoard2D(int numPlayer) throws IOException {
    /* Settings */
    this.setLayout(null);
    this.setSize(650, 889);

    String imgCmd = "media/Design/commandes/c" + numPlayer + ".png";
    img = ImageIO.read(new File(imgCmd));

    int sx = 300, sy = 50;
    int x = WINDOW_WIDTH / 2 - sx / 2;
    int y = 780;
    if (numPlayer == 0) {
      this.add(play);
    }

    if (numPlayer != 2) {
      namePlayer.setBackground(Color.gray);
      namePlayer.setFont(new Font("Champagne & Limousines", 30, 30));
      namePlayer.setHorizontalAlignment(JTextField.CENTER);
      namePlayer.setBounds(x, y - 50, sx, sy);
      this.add(namePlayer);
    }
  }

  /**
   * Get entered Player's name.
   *
   * @return Player's name
   */
  public String getPlayerName() {
    return namePlayer.getText();
  }

  /**
   * Override of paintComponent function to draw the background image.
   *
   * @param g Graphics to draw on
   */
  @Override
  public void paintComponent(Graphics g) {
    g.drawImage(img, 0, 0, this);
  }
}
