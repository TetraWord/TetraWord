
package GameEngine;

//Singleton

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ShapesStock {
  
  private final Shape[] shapeModel;
  private final Queue<Shape> instanciedShape = new LinkedList<>();
  
  private ShapesStock() {
      /*Read files .shape*/
      File repertoire = new File("media/Shapes/");
      String[] liste = repertoire.list();
      int cpt = 0;
      for( int i = 0; i < liste.length; ++i){
          if( liste[i].endsWith(".shape") ){ 
            System.out.println( liste[i] +"\n");
            ++cpt;
          }
      }
      shapeModel = new Shape[cpt];
      /*Pour chaque fichier .shape créer une shape*/
      /*Stocker les shape dans le ShapesStock*/
  }
  
  public static ShapesStock getInstance() {
    return ShapesStockHolder.INSTANCE;
  }
  
  public void readFile(){
    
  }
  
  public Shape getRandomShape(){
    Random r = new Random();
    int x = Math.abs(r.nextInt()) % shapeModel.length + 1;
    Shape newShape = new Shape( shapeModel[x] );
    instanciedShape.add(newShape);
    return newShape;
  }
  
  public static void main(String[] args){
      ShapesStock ss = new ShapesStock();
  }
  
  private static class ShapesStockHolder {

    private static final ShapesStock INSTANCE = new ShapesStock();
  }
}
