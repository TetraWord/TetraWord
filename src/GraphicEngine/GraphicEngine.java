package GraphicEngine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class GraphicEngine {

	public static final int WINDOW_WIDTH = 650;
	public static final int WINDOW_HEIGHT = 889;

	private static final GraphicEngine INSTANCE = new GraphicEngine();
	private final Window window;

	//Singleton
	private GraphicEngine() {
		window = new Window();
	}

	public static GraphicEngine getInstance() {
		return INSTANCE;
	}

	public void init() {
		window.defineMainMenu();
		
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("conf/myConf.properties");

			// set the properties value
			prop.setProperty("background", "media/Design/paper/background.jpg");
			prop.setProperty("brick", "media/Design/paper/brick.png");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public Window getWindow() {
		return window;
	}

	public void renderFrame() {
		window.repaint();
	}

}
