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

public class OverBoard2D extends JPanel {

  private final Image img;
  private final JTextField namePlayer;
	//private final Menu2D menu;

  public OverBoard2D(int numPlayer) throws IOException {
    /* Settings */
    this.setLayout(null);
    this.setSize(650, 889);

    String imgCmd = "media/Design/commandes/c" + numPlayer + ".png";
    img = ImageIO.read(new File(imgCmd));

    int sx = 300, sy = 50;
    int x = WINDOW_WIDTH / 2 - sx / 2;
    int y = 780;

    Button2D b = new Button2D("Jouer", x, y, sx, sy);
    this.add(b);

    namePlayer = new JTextField("Pseudo");
    namePlayer.setBackground(Color.gray);
    namePlayer.setFont(new Font("Champagne & Limousines", 30, 30));
    namePlayer.setHorizontalAlignment(JTextField.CENTER);
    namePlayer.setBounds(x, y - 50, sx, sy);
    this.add(namePlayer);
  }

  public String getPlayerName() {
    return namePlayer.getText();
  }

  @Override
  public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
  }
}
