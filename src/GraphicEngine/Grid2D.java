package GraphicEngine;

import GameEngine.Brick;
import GameEngine.CurrentShape;
import GameEngine.Grid;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

class Grid2D extends JPanel implements Observer {

  private Grid model = null;
  private CurrentShape2D currentShape = null;
  private final Brick2D[][] compositionBrick2D;
	private final boolean shadowed;

  public Grid2D(Grid model, boolean shadow) {
    this.model = model;
    this.setVisible(true);
		this.shadowed = shadow;						

		Brick[][] tabBrick = model.getTGrid();
		compositionBrick2D = new Brick2D[tabBrick.length][tabBrick[0].length];

  }

  public void setShape2D(CurrentShape s) {
    currentShape = new CurrentShape2D(s);
    s.addObservateur(currentShape);
  }

  @Override
  public void paintComponent(Graphics g) {
    //Grid draw
    int top = 135;
    int left = 70;
    int sizeBrick = 35;

    if (currentShape != null) {
      currentShape.paintComponent(g);
			if (shadowed) {
				currentShape.paintShadow(g, model.getTGrid());
			}
    }
    //Brick draw
    Brick[][] t = model.getTGrid();

    for (int i = 0; i < t.length; ++i) {
      for (int j = 0; j < t[i].length; ++j) {
        //System.out.println(i+" "+j);
        Brick b = t[i][j];
        if (t[i][j].getNb() > 0 && compositionBrick2D[i][j] != null) {
          if (i == 0 && j == 0) {
            System.out.println("ahah");
          }
          BufferedImage monImage = currentShape.getBrickImage(b.getColor());
					compositionBrick2D[i][j].draw(g, j * sizeBrick + left, i * sizeBrick + top, monImage, false);
        }
      }
    }
  }

  @Override
  public void update(Observable o, Object args) {
    if (args instanceof CurrentShape) {
      saveBrick();
      setShape2D((CurrentShape) args);
    }
    if (args instanceof Brick) {
      /*We put a new brick in the grid -> create a brick2D*/
      saveBrick();
    }
    if (args instanceof Brick[][]) {
      /*We put a new brick in the grid -> create a brick2D*/
      updateCompositionBrick2D((Brick[][]) args);
    }
  }

  private void saveBrick() {
    CurrentShape cs = (CurrentShape) currentShape.getModel();
    Brick[][] tabBrick = cs.getComposition();
    int xCS = cs.getX();
    int yCS = cs.getY();

    int top = 135;
    int left = 70;
    int sizeBrick = 35;
    int x, y;
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        x = (j + xCS);
        y = (i + yCS);
        if (tabBrick[i][j] != null) {
          if (tabBrick[i][j].getNb() > 0) {
            //System.out.println(x + "" + y);
            compositionBrick2D[y][x] = new Brick2D(tabBrick[i][j]);
          }
        }
      }
    }
  }

  private void updateCompositionBrick2D(Brick[][] bricks) {
    for (int i = 0; i < bricks.length; ++i) {
      for (int j = 0; j < bricks[i].length; ++j) {
        if (bricks[i][j].getNb() > 0) {
          compositionBrick2D[i][j] = new Brick2D(bricks[i][j]);
        }
      }
    }
  }

}
