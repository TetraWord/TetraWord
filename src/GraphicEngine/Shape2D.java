package GraphicEngine;

import GameEngine.Brick;
import GameEngine.Shape;
import java.awt.Graphics;

/**
 * <b> Shape2D is the graphic representation of a Shape. </b>
 * <p>
 * Shape2d contains:
 * <ul>
 * <li> A Shape logical model. </li>
 * <li> An array of Brick2D for it composition.
 * </li>
 * </ul>
 *
 * </p>
 *
 * @see Shape
 */
public class Shape2D {

	/**
	 * Shape logical model.
	 *
	 * @see Shape
	 */
	private final Shape model;

	/**
	 * Array of Brick2D for its composition.
	 */
	protected final Brick2D[][] compositionBrick2D;

	/**
	 * Shape2D constructor Create a Shape2D from its Shape model
	 *
	 * @param s Shape logical model
	 */
	public Shape2D(Shape s) {
		this.model = s;

		int[][] representation = model.getRepresentation();
		Brick[][] composition = model.getComposition();
		compositionBrick2D = new Brick2D[composition.length][composition[0].length];
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				/* If there if a brick in the model */
				if (representation[i][j] > 0) {
					/* Create a Brick2D */
					compositionBrick2D[i][j] = new Brick2D(composition[i][j]);
				} else {
					compositionBrick2D[i][j] = null;
				}
			}
		}
	}

	/**
	 * Draw a Shape2D
	 *
	 * @param g Graphics to draw on
	 * @param x Horizontal position
	 * @param y Vertical position
	 * @param ratio Ratio of the drawing (from 0 to 1)
	 */
	public void draw(Graphics g, int x, int y, double ratio) {
		int top = y;
		int left = x;
		int sizeBrick = (int) (35 * ratio);

		int[][] representation = model.getRepresentation();
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (representation[i][j] > 0) {
					/* If there s a Brick2D */
					if (compositionBrick2D[i][j] != null) {
						/* Drax it ! */
						compositionBrick2D[i][j].draw(g, j * sizeBrick + left, i * sizeBrick + top, ratio, false);
					}
				}
			}
		}
	}

}
