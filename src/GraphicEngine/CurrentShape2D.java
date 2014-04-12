package GraphicEngine;

import GameEngine.CurrentShape;
import GameEngine.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

//Draw the currentShape
public class CurrentShape2D extends Shape2D {

	private CurrentShape model;

	public CurrentShape2D(CurrentShape model) {
    super(model);
		this.model = model;
	}

	public void paintComponent(Graphics g) {
		/*Drawing of a brick whith the color of the model */
      Color c = new Color(model.getR(), model.getG(), model.getB());
      BufferedImage monImage = getBrickImage(c);

      //Draw the shape
			//70 en x et 135 en y pour le coin haut gauche
			int top = 135;
			int left = 70;
			int sizeBrick = 35;
			int[][] representation = model.getRepresentation();
			for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					if (representation[i][j] > 0) {
						g.drawImage(monImage, (j + model.getX()) * sizeBrick + left, (i + model.getY()) * sizeBrick + top, null);
					}
				}
			}
		
	}
	
	public void paintShadow(Graphics g) {
		/*Drawing of a shadow of brick with the color of the model */
    Color c = new Color(model.getR(), model.getG(), model.getB());
    BufferedImage monImage = getBrickShadow(c);

    //Draw the shape
		//70 en x et 135 en y pour le coin haut gauche
		int top = 135;
		int left = 70;
		int sizeBrick = 35;
		int[][] representation = model.getRepresentation();
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (representation[i][j] > 0) {
					g.drawImage(monImage, (j + model.getX()) * sizeBrick + left, (i + 18) * sizeBrick + top, null);
				}
			}
		}
	}

  Shape getModel() {
    return model;
  }
}
