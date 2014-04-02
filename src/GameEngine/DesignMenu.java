
package GameEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DesignMenu {

	private final String[] name;
	private final String[] background;
	private final String[][] bricks;

	
	public DesignMenu() {
		/*Read design conf files */
		File repertoire = new File("conf/Design/");
		String[] liste = repertoire.list();
		
		name = new String[liste.length];
		background = new String[liste.length];
		bricks = new String[liste.length][];
		
		for (int i = 0; i < liste.length; ++i) {
		
			if (liste[i].endsWith(".properties")) {
				/*For each files, save background*/
				readFile("conf/Design/"+liste[i], i);
			}
		}
	}

	private void readFile(String file, int i) {
		
		Properties prop = new Properties();
		InputStream input = null;
		/*Get infos from file*/
		try {

			input = new FileInputStream(file);

			// load a properties file
			prop.load(input);

			name[i] = prop.getProperty("name");
			background[i] = prop.getProperty("background");
			String[] listeBrick = prop.getProperty("brick").split(",");
			bricks[i] = new String[listeBrick.length];
			for (int j = 0; j < listeBrick.length; ++j ){
				bricks[i][j] = listeBrick[j];
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args){
		DesignMenu dm = new DesignMenu();
	}

	public String[] getNames() {
		return name;
	}

	public String getBackground(int i) {
		return background[i];
	}

	public String[] getBricks(int i) {
		return bricks[i];
	}
}
