package GraphicEngine;

import GameEngine.DesignMenu;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import static GraphicEngine.GraphicEngine.WINDOW_HEIGHT;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;

public class DesignMenu2D extends Menu2D {

	DesignMenu dm;
	String apercuBackground;
	String[] bricks;
	ButtonGroup group = new ButtonGroup();

	public DesignMenu2D() {
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
		bricks = dm.getBricks(0);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawApercuBackground(g, apercuBackground);
		drawApercuBricks(g, bricks);
	}

	private void drawApercuBackground(Graphics g, String file) {
		try {
			Image img = ImageIO.read(new File(apercuBackground));
			int width = WINDOW_WIDTH / 3;
			int height = WINDOW_HEIGHT / 3;
			g.drawImage(img, WINDOW_WIDTH / 2 - width / 2, 150, width, height, this);
		} catch (IOException ex) {
			Logger.getLogger(Menu2D.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void drawApercuBricks(Graphics g, String[] bricks) {
		int x = WINDOW_WIDTH / 4;
		int y = WINDOW_HEIGHT / 2 + 100;
		int step = WINDOW_WIDTH / 7;

		JLabel label = new JLabel("Choose your bricks");

		for (int i = 0; i < bricks.length; ++i) {
			try {
				int width = 35;
				int height = 35;

				Image img = ImageIO.read(new File(bricks[i]));
				g.drawImage(img, x + i * step, y, width, height, this);

			} catch (IOException ex) {
				Logger.getLogger(Menu2D.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	void reloadApercuBackground(String item) {
		String[] themes = dm.getNames();
		for (int i = 0; i < themes.length; ++i) {
			if (themes[i].equals(item)) {
				apercuBackground = dm.getBackground(i);
				bricks = dm.getBricks(i);
			}
		}
		while (group.getElements().hasMoreElements()) {
			System.out.println(group.getElements().nextElement());
			group.remove(group.getElements().nextElement());
		}
		for (int i = 0; i < bricks.length; ++i) {
			int x = WINDOW_WIDTH / 4;
			int y = WINDOW_HEIGHT / 2 + 100;
			int step = WINDOW_WIDTH / 7;
			RadioButton2D radio = new RadioButton2D("Brick");
			radio.setText("");
			radio.setBounds(x + 8 + i * step, y + 40, 20, 20);
			if (i == 1) {
				radio.setSelected(true);
			}
			group.add(radio);
			//this.add(group);
		}
	}
}
