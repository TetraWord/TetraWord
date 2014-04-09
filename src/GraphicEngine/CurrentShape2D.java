package GraphicEngine;

import GameEngine.Brick;
import GameEngine.CurrentShape;
import GameEngine.Shape;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

//Draw the currentShape
public class CurrentShape2D extends Shape2D implements Observer{

	private final CurrentShape model;

	public CurrentShape2D(CurrentShape model) {
    super(model);
		this.model = model;
	}

	public void paintComponent(Graphics g) {
		int top = 135;
		int left = 70;
		int sizeBrick = 35;
		Color c = model.getColor();
    BufferedImage monImage = getBrickImage(c);
		for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					if (compositionBrick2D[i][j] != null) {
						compositionBrick2D[i][j].draw(g, (j + model.getX()) * sizeBrick + left, (i + model.getY()) * sizeBrick + top, monImage);
					}
				}
			}
		
	}

  Shape getModel() {
    return model;
  }

	@Override
	public void update(Observable o, Object args) {
		
		int[][] representation = model.getRepresentation();
		Brick[][] composition = model.getComposition();
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
}
