package GameEngine;

import java.util.Random;

import GameEngine.Modifier;

public class CurrentModifier extends Modifier {
	private int coordX;
	private int coordY;
	
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

  public int getX() {
  	return this.coordX;
  }
  
  public int getY() {
  	return this.coordY;
  }
  
  private boolean displayOnABrick(Brick [][] gridRep, int x, int y) {
  	if(gridRep[y][x].getNb() >= 1) {
  		return true;
  	}
  	return false;
  }

}
