package GameEngine;

import java.util.Random;


/**
 * <b> CurrentModidier is a logical part of the game representing the current modifier presents in the grid.</b>
 * <p>
 * The current modifier inherits from Modifier
 * </p>
 * <p>
 * A current modifier contains:
 * <ul>
 * <li> Coordinates in x and y </li>
 * </ul>
 * </p>
 * 
 * @see Modifier
 */

public class CurrentModifier extends Modifier {
	private final int coordX;
	private final int coordY;
	
	/**
	 * Create a current Modifier with random coordinates
	 * Call displayOnBrick to verify if the coordinates is free, else try others coordinates
	 * 
	 * @param gridRep: an array of Brick to verify if the coordinates are free in grid
	 */
	public CurrentModifier(Brick [][] gridRep) {
		super();
		Random r = new Random();
		int x = r.nextInt(10);
		int y = r.nextInt(18);
		while(this.displayOnABrick(gridRep, x, y+2)){
			x = r.nextInt(10);
			y = r.nextInt(18);
		}
		this.coordX = x;
		this.coordY = y+2;
	}

  /**
   * Get the horizontal value of coordinates
   * 
   * @return the horizontal value of coordinates
   */
  public int getX() {
  	return this.coordX;
  }
  
  /**
   * Get the vertical value of coordinates
   * 
   * @return the vertical value of coordinates
   */
  public int getY() {
  	return this.coordY;
  }
  
  /**
   * Verify if the coordinates are free
   * Is used in constructor
   * 
   * @param gridRep: an array of Brick
   * @param x: the horizontal value of coordinates
   * @param y: the vertical value of coordinates
   * 
   * @return boolean
   */
  private boolean displayOnABrick(Brick [][] gridRep, int x, int y) {
  	return gridRep[y][x].getNb() >= 1;
  }

}
