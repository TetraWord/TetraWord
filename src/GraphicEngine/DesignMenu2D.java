package GraphicEngine;

import GameEngine.DesignMenu;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class DesignMenu2D extends Menu2D{
	
	DesignMenu dm;
	String apercuBackground;
	
	public DesignMenu2D(){
		super();
		state = Menu2D.MenuState.DESIGN;
		
		int sx = 150, sy = 60;
		int x = WINDOW_WIDTH / 2 - sx;
		int y = 50;
		int step_y = y + sy;
		int step_x = y + sx;

		dm = new DesignMenu();

		JLabel label = new JLabel("Choose a theme");
		label.setBounds(x, y, sx, sy);
		this.add(label);

		x = x + step_x;
		ComboBox combo = new ComboBox("Choose theme", dm.getNames(), x, y, sx, sy);
		this.add(combo);
		
		apercuBackground = dm.getBackground(0);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawApercuBackground(g, apercuBackground);
	}

	private void drawApercuBackground(Graphics g, String file) {
		try {
			Image img = ImageIO.read(new File(apercuBackground));
			int width = 650/3;
			int height = 860/3;
			g.drawImage(img, WINDOW_WIDTH/2 - width/2, 150, width, height, this);
		} catch (IOException ex) {
			Logger.getLogger(Menu2D.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	void reloadApercuBackground(String item) {
		String[] themes = dm.getNames();
		for(int i = 0; i < themes.length; ++i){
			System.out.println(themes[i]);
			if( themes[i].equals(item) ){
				apercuBackground = dm.getBackground(i);
			}
		}
	}
}
