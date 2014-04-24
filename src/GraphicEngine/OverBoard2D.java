
package GraphicEngine;

import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OverBoard2D extends JPanel{
	
	private final String imgCmd;
	//private final Menu2D menu;
	
	public OverBoard2D(int numPlayer){
		/* Settings */
    this.setLayout(null);
    this.setSize(650, 889);
		
		this.imgCmd = "media/Design/commandes/c" + numPlayer + ".png";
		
		int sx = 300, sy = 50;
    int x = WINDOW_WIDTH / 2 - sx / 2;
    int y = 720;
		
		Button2D b = new Button2D("Jouer", x, y, sx, sy);
		this.add(b);
	}
	
	@Override 
	public void paintComponent(Graphics g){
		try {
      /*Try to load background image from chosen design*/

      Image img = ImageIO.read(new File(imgCmd));
      g.drawImage(img, 0, 0, this);
    } catch (IOException e) {
      /*Load background image from default design*/
      e.printStackTrace();
    }
	}
}
