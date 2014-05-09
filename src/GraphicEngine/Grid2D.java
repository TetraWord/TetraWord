package GraphicEngine;

import ContextManager.GridEventListener;
import GameEngine.Brick;
import GameEngine.CurrentModifier;
import GameEngine.CurrentShape;
import GameEngine.Grid;
import GameEngine.Player;
import Pattern.Observable;
import Pattern.Observer;

import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JPanel;

public class Grid2D extends JPanel implements Observer {

  private Grid model = null;
  private CurrentShape2D currentShape = null;
  private final Brick2D[][] compositionBrick2D;
  private final BrickButton[][] clickGrid;
  private final boolean shadowed;
  private Modifier2D currentModifier = null;

  public Grid2D(Grid model, GridEventListener event, boolean shadow) {
    this.model = model;
    this.shadowed = shadow;

    this.setLayout(null);
    this.setSize(350, 700);
    this.setOpaque(false);
    this.setVisible(true);

    this.clickGrid = new BrickButton[20][10];
    for (int i = 0; i < 20; ++i) {
      for (int j = 0; j < 10; ++j) {
        BrickButton b = new BrickButton(j, i, event);
        clickGrid[i][j] = b;
        this.add(clickGrid[i][j]);
      }
    }

    Brick[][] tabBrick = model.getTGrid();
    compositionBrick2D = new Brick2D[tabBrick.length][tabBrick[0].length];
		
		
    Properties prop = new Properties();
		InputStream input = null;
		
		/*Get brick image from file design*/
    try {

      input = new FileInputStream("conf/design.properties");

      // load a properties file
      prop.load(input);
			Brick2D.setBrickImage(prop.getProperty("brick"));
			Modifier2D.setModifierImage(prop.getProperty("modifier"));

    } catch (IOException ex) {
      /*Default background*/
			Brick2D.setBrickImage("media/Design/paper/brick.png");
			Modifier2D.setModifierImage("media/Design/paper/modifier.png");
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    this.setVisible(true);
  }

  public void setShape2D(CurrentShape s) {
    currentShape = new CurrentShape2D(s);
    s.addObservateur(currentShape);
  }
  
  public void setModifier2D(CurrentModifier m) {
  	if(m != null){
    	currentModifier = new Modifier2D(m);
  	}
  }
  
  public void removeModifier2D(){
  	currentModifier = null;
  }

  public void draw(Graphics g, int offsetX, int offsetY) {
    //Grid draw
    int top = 135 + offsetY;
    int left = 70 + offsetX;
    int sizeBrick = 35;
    Player myPlayer = model.getPlayer();
    
    if(currentModifier != null && myPlayer.isTetris()) {
    	currentModifier.draw(g, offsetX , offsetY, 1);
    }

    if (currentShape != null && myPlayer.isTetris()) {
      currentShape.draw(g, offsetX, offsetY);
      if (shadowed) {
        currentShape.paintShadow(g, model.getTGrid(), offsetX, offsetY);
      }
    }
		
    //Brick draw
    Brick[][] t = model.getTGrid();

    for (int i = 0; i < t.length; ++i) {
      for (int j = 0; j < t[i].length; ++j) {
        Brick b = t[i][j];
        if (b.getNb() > 0 && compositionBrick2D[i][j] != null) {
          compositionBrick2D[i][j].draw(g, j * sizeBrick + left, i * sizeBrick + top, 1, false);
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
    if (args instanceof CurrentModifier) {
      /*We put a new modifier in the grid -> create a modifier2D*/
      setModifier2D((CurrentModifier) args);
    }
    if (args == null) {
      /*We remove the modifier*/
      removeModifier2D();
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

    int x, y;
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        x = (j + xCS);
        y = (i + yCS);
        if (tabBrick[i][j] != null) {
          if (tabBrick[i][j].getNb() > 0) {
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
