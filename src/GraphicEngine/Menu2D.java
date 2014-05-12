package GraphicEngine;

import GameEngine.MenuState;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * <b> Menu2D is a graphic menu. </b>
 * <p>
 * Menu2D inherits from JPanel. </p>
 *
 * @see JPanel
 */
public class Menu2D extends JPanel {

	/**
	 * Array of Button2D.
	 */
	private Button2D[] buttons;

	/**
	 * Background image of the Menu2D.
	 */
	private Image img;

	/**
	 * Number of Button2D in the Menu2D.
	 */
	private int capacity;

	/**
	 * Actual number of Button2D in the Menu2D.
	 */
	private int current;

	/**
	 * Current state.
	 */
	protected MenuState state = MenuState.MAIN;

	/**
	 * Previous state.
	 */
	protected MenuState lastState = null;

	/**
	 * Menu2D constructor. Initialize some features.
	 */
	public Menu2D() {
		//Panel settings
		this.setLayout(null);

		//Menu settings
		capacity = 0;
		current = 0;

		loadBackground();
	}

	/**
	 * Load background image for the Menu2D.
	 */
	protected final void loadBackground() {

		if (state != MenuState.DESIGN && state != MenuState.DIFFICULTY) {
			try {
				/*Try to load background image from chosen design*/

				img = ImageIO.read(new File("media/Design/menuBG.jpg"));
			} catch (IOException e) {
				/*Load background image from default design*/
				e.printStackTrace();
			}
		} else {
			try {
				/*Try to load background image from chosen design*/

				img = ImageIO.read(new File("media/Design/optionMenuBG.jpg"));
			} catch (IOException e) {
				/*Load background image from default design*/
				e.printStackTrace();
			}

		}
	}

	/**
	 * Set the capacity of the Menu2D.
	 *
	 * @param capacity Capacity of the Menu2D
	 */
	protected void setButton2D(int capacity) {
		this.capacity = capacity;
		buttons = new Button2D[capacity];
		current = 0;
	}

	/**
	 * Get state menu.
	 *
	 * @return State menu
	 */
	public MenuState getState() {
		return state;
	}

	/**
	 * Get previous state menu.
	 *
	 * @return Previous state menu
	 */
	public MenuState getLastState() {
		return lastState;
	}

	/**
	 * Add a Button2D to the Menu2D.
	 *
	 * @param newButton Button2D to add
	 */
	public void add(Button2D newButton) {
		if (current < capacity) {
			buttons[current] = newButton;
			super.add(buttons[current]);
			++current;
		}
	}

	/**
	 * Get all Button2D of the menu.
	 *
	 * @return Array of Button2D
	 */
	public Button2D[] getButtons() {
		return buttons;
	}

	/**
	 * Get the Button2D at the position i.
	 *
	 * @param index Position of the wanted Button2D
	 * @return Wanted Button2D
	 */
	public Button2D getAt(int index) {
		return buttons[index];
	}

	/**
	 * Inititialize features for main menu.
	 */
	public void defineMainMenu() {
		state = MenuState.MAIN;
		lastState = null;

		setButton2D(4);
		int sx = 300, sy = 50;
		int x = WINDOW_WIDTH / 2 - sx / 2;
		int y = 300;
		int step_y = 50 + sy;
		this.add(new Button2D("Nouvelle partie", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Charger partie", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Options", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Quitter", x, y, sx, sy));

		loadBackground();
	}

	/**
	 * Inititialize features for game menu.
	 */
	public void defineGameMenu() {
		state = MenuState.GAME;
		lastState = MenuState.MAIN;

		setButton2D(3);
		int sx = 300, sy = 50;
		int x = WINDOW_WIDTH / 2 - sx / 2;
		int y = 300;
		int step_y = 50 + sy;
		this.add(new Button2D("Jouer seul", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Jouer à plusieurs", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Précédent", x, y, sx, sy));

		loadBackground();
	}

	/**
	 * Inititialize features for options menu.
	 */
	public void defineOptionGameMenu() {
		state = MenuState.OPTION;
		lastState = MenuState.MAIN;

		setButton2D(4);
		int sx = 300, sy = 50;
		int x = WINDOW_WIDTH / 2 - sx / 2;
		int y = 300;
		int step_y = 50 + sy;
		this.add(new Button2D("Préférences", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Editeur de pièces", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Difficulté", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Précédent", x, y, sx, sy));

		loadBackground();
	}

	/**
	 * Inititialize features for multiplayers menu.
	 */
	void defineMultiPlayersMenu() {
		lastState = state;
		state = MenuState.MULTI;

		setButton2D(3);
		int sx = 300, sy = 50;
		int x = WINDOW_WIDTH / 2 - sx / 2;
		int y = 300;
		int step_y = 50 + sy;
		this.add(new Button2D("Contre un joueur", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Contre un IA", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Précédent", x, y, sx, sy));

		loadBackground();
	}

	/**
	 * Inititialize features for pause menu.
	 */
	void definePauseMenu() {
		lastState = state;
		state = MenuState.GAME;

		setButton2D(3);
		int sx = 300, sy = 50;
		int x = WINDOW_WIDTH / 2 - sx / 2;
		int y = 300;
		int step_y = 50 + sy;
		this.add(new Button2D("Reprendre", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Sauvegarder", x, y, sx, sy));
		y = y + step_y;
		this.add(new Button2D("Quitter", x, y, sx, sy));

		loadBackground();
	}

	/**
	 * Override paintComponentFunction to draw backgroundImage.
	 *
	 * @param g Graphics to draw on
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}
}
