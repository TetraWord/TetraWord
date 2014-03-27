
package GameEngine;

//Singleton

import java.io.File;

public class ShapesStock {
  
  private ShapesStock() {
      /*Lire les fichiers .shape*/
      File repertoire = new File("media/Shapes/");
      String[] liste = repertoire.list();
      for( int i = 0; i < liste.length; ++i){
          if( liste[i].endsWith(".shape") ){ 
            System.out.println( liste[i] +"\n");
          }
      }
      /*Pour chaque fichier .shape créer une shape*/
      /*Stocker les shape dans le ShapesStock*/
  }
  
  public static ShapesStock getInstance() {
    return ShapesStockHolder.INSTANCE;
  }
  
  public void readFile(){
    
  }
  
  public void getRandomShape(){
   
  }
  
  public static void main(String[] args){
      ShapesStock ss = new ShapesStock();
  }
  
  private static class ShapesStockHolder {

    private static final ShapesStock INSTANCE = new ShapesStock();
  }
}
