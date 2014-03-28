
package GraphicEngine;

import GameEngine.Shape;
import java.awt.Color;
import java.awt.Graphics;

class Shape2D {
  private Shape model;
  /*
  public Shape2D(Shape model){
    this.model = model;
  }*/
  
  public Shape2D(){
    System.out.println("je crée une shape 2D");
  }
  
  public void paintComponent (Graphics g){
    g.setColor(new Color(255,0,0));
    g.fillRect(1, 1, 35, 35);
    g.fillRect(35, 1, 35, 35);
    g.fillRect(70, 1, 35, 35);
    g.fillRect(35, 35, 35, 35);
  }
}
