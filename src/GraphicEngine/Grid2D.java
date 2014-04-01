package GraphicEngine;

import GameEngine.CurrentShape;
import GameEngine.Grid;
import Pattern.Observable;
import Pattern.Observer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

class Grid2D extends JPanel implements Observer{

  private Grid model = null;
  private Shape2D currentShape = null;

  public Grid2D(Grid model) {
    this.model = model;
    this.setVisible(true);
  }

  public void setShape2D(CurrentShape s) {
    currentShape = new Shape2D(s);
  }

  @Override
  public void paintComponent(Graphics g) {
    //Grid draw
    int top = 135;
    int left = 70;
    int sizeBrick = 35;

    if (currentShape != null) {
      currentShape.paintComponent(g);
    }
    int[][] t = model.getTGrid();
    for (int i = 0; i < t.length; ++i) {
      for (int j = 0; j < t[i].length; ++j) {
        if (t[i][j] > 0) {
          BufferedImage monImage = currentShape.getBrickImage(g, currentShape.getModel());
          g.drawImage(monImage, j * sizeBrick + left, i * sizeBrick + top, null);
        }
      }
    }
  }

  @Override
  public void update(Observable o, Object args) {
    if(args instanceof CurrentShape){
      setShape2D((CurrentShape)args);
    }
  }

}
