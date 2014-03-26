
package GameEngine;

//Singleton
public class ShapesStock {
  
  private ShapesStock() {
  }
  
  public static ShapesStock getInstance() {
    return ShapesStockHolder.INSTANCE;
  }
  
  public void readFile(){
    
  }
  
  public void getRandomShape(){
   
  }
  
  private static class ShapesStockHolder {

    private static final ShapesStock INSTANCE = new ShapesStock();
  }
}
