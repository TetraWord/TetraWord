package GraphicEngine;

import GameEngine.Brick;
import GameEngine.Shape;
import java.awt.Graphics;


public class Shape2D {

  private final Shape model;
	protected final Brick2D[][] compositionBrick2D;
  private String brick;
	
  public Shape2D(Shape s){
    this.model = s;
 
		int[][] representation = model.getRepresentation();
		Brick[][] composition = model.getComposition();
		compositionBrick2D = new Brick2D[composition.length][composition[0].length];
		for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					if (representation[i][j] > 0) {
						compositionBrick2D[i][j] = new Brick2D(composition[i][j]);
					} else {
						compositionBrick2D[i][j] = null;
					}
				}
			}
  }

  public void draw(Graphics g, int x, int y, double ratio) {		
		int top = y;
		int left = x;
		int sizeBrick = (int)(35 * ratio);
		
			int[][] representation = model.getRepresentation();
			for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					if (representation[i][j] > 0) {
						if (compositionBrick2D[i][j] != null) {
							compositionBrick2D[i][j].draw(g, j * sizeBrick + left, i * sizeBrick + top, ratio, false);
						}
					}
				}
			}		
	}

}
