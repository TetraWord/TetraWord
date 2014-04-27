package GameEngine;

import ContextManager.ContextManager;
import GameEngine.Dictionnary.Dictionnary;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IA extends Player implements Runnable {

  private int finalX;
  private int finalY;
  private int nbRotation;
  private CurrentShape lastShape;

  public IA(int nb, Shape s, Shape s2, Dictionnary d) {
    super(nb, s, s2, d);
  }

  public void chooseShapePosition() {
    CurrentShape s = getCurrentShape();
    Grid g = boardGame.getGrid();
    int tmpY, coeffY, tmpRotation, coeffRotation, bestPosY = 0, bestPosX = 0, coeff = 0, bestCoeff = 0, rotate = 0;
    
    
    for (int i = 0; i < 4; ++i) {
      
      int w = s.getWidth();
      int h = s.getHeight(0);
            
      for (int x = 0; x < Grid.sizeX - s.getWidth() + 1; ++x) {
        coeffY = 20;
        
        
          System.out.println("x vaut "+x);
        //Pour chacune des colonnes, on fait rotater la pièce et on cherche le meilleur emplacement pour positionner la pièce.

        //L'endroit où la pièce sera la moins haute
        for (int j = 0; j < s.getWidth(); ++j) {
          tmpY = g.getMaxY(x + j) - s.getHeight(j);
          if (coeffY > tmpY) {
            coeffY = tmpY;
          }
        }
        
        if (bestPosY < coeffY) {
          bestPosY = coeffY;
          bestPosX = x;
          rotate = i;
        }
      }
      
      s.rotateLeft(g.getTGrid());
    }
    
          
    finalX = bestPosX;
    finalY = g.getMaxY(finalX);
    nbRotation = rotate;

    System.out.println("finalX : " + finalX);
    System.out.println("finalY : " + finalY);
    System.out.println("finalRotate : " + nbRotation);
  }

  public void chooseAnagram() {

  }

  public void move() {
    CurrentShape s = getCurrentShape();
    int curX = s.getX(), curY = s.getY();
    if (nbRotation != 0) {
      s.rotateLeft(boardGame.getGrid().getTGrid());
      --nbRotation;
    } else if (finalX != curX) {
      if (curX > finalX) {
        left();
      } else {
        right();
      }
    } else {
      dropDown();
    }
  }

  @Override
  public void run() {
    lastShape = getCurrentShape();
    chooseShapePosition();
    while (!this.isFinish()) {
      if (!ContextManager.getInstance().isPaused && isTetris()) {
        if (lastShape != getCurrentShape()) {
          lastShape = getCurrentShape();
          chooseShapePosition();
        }
        int nbMove = (int) (Math.random() * 3);
        while (nbMove > 0) {
          move();
          nbMove--;
        }
        down(1);
        try {
          Thread.sleep(getSpeedFall());
        } catch (InterruptedException ex) {
          Logger.getLogger(RunPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else if (isAnagram()) {
        doAnagram();
      } else if (isWorddle()) {
        doWorddle();
      }

      updateObservateur(null);
    }

    updateObservateur("Perdu");

    ContextManager.getInstance().stop();
  }

}
