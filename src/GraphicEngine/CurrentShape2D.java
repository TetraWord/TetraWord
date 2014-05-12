package GraphicEngine;

import GameEngine.Brick;
import GameEngine.CurrentShape;
import GameEngine.Shape;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Graphics;

/**
 * <b> CurrentShape2D is a graphical part representing the shape that fall in
 * the tetris grid. </b>
 * <p>
 * CurrentShape2D is an Observer of its logical model, the CurrentShape. </p>
 * <p>
 * It contains its CurrentShape model. </p>
 *
 * @see Shape2D
 */
public class CurrentShape2D extends Shape2D implements Observer {

  private final CurrentShape model;

  /**
   * Constructor. Call the Shape2D constructor and associate the model.
   *
   * @param model
   */
  public CurrentShape2D(CurrentShape model) {
    super(model);
    this.model = model;
  }

  /**
   * Get the CurrentShape's model.
   *
   * @return The CurrentShape
   */
  Shape getModel() {
    return model;
  }

  /**
   * Draw the CurrentShape2D in the Grid2D.
   *
   * @param g Graphics to draw on
   * @param offsetX The offset on x axe
   * @param offsetY The offset on y axe
   */
  public void draw(Graphics g, int offsetX, int offsetY) {
    int top = 135 + offsetY;
    int left = 70 + offsetX;
    int sizeBrick = 35;
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        if (compositionBrick2D[i][j] != null) {
          compositionBrick2D[i][j].draw(g, (j + model.getX()) * sizeBrick + left, (i + model.getY()) * sizeBrick + top, 1, false);
        }
      }
    }

  }

  /**
   * Paint the shadow of the CurrentShape2D.
   *
   * @param g Graphics to draw on
   * @param matrix The CurrentShape2D's composition
   * @param offsetX The offset on x axe
   * @param offsetY The offset on y axe
   */
  public void paintShadow(Graphics g, Brick[][] matrix, int offsetX, int offsetY) {
    int top = 135 + offsetY;
    int left = 70 + offsetX;
    int sizeBrick = 35;

    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        if (compositionBrick2D[i][j] != null) {
          compositionBrick2D[i][j].draw(g, (j + model.getX()) * sizeBrick + left, (i + model.getFinalLine(matrix)) * sizeBrick + top, 1, true);
        }
      }
    }
  }

  /**
   * Update the Shape2D composition.
   *
   * @see Observer
   */
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
