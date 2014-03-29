
package GameEngine;

//Singleton

//import Tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public final class ShapesStock {
  
  private final Shape[] shapeModel;
  private final Queue<Shape> instanciedShape = new LinkedList<>();
  
  private ShapesStock() {
      /*Read files .shape*/
      int cpt = 0;
      File repertoire = new File("media/Shapes/");
      String[] liste = repertoire.list();
      shapeModel = new Shape[liste.length - 1];
      for( int i = 0; i < liste.length; ++i){
          if( liste[i].endsWith(".shape") ){ 
          	/*Pour chaque fichier .shape créer une shape et l'ajouter au shapeModel*/
            shapeModel[cpt++] = this.readFile("media/Shapes/"+liste[i]);
          }
      }
  }
  
  public static ShapesStock getInstance() {
    return ShapesStockHolder.INSTANCE;
  }
  
  private Shape readFile(String path){
  	Scanner scanner;
  	int count = 0;
  	int i, cpt;
  	StringReader l;
  	String name ="";
  	int[] color = new int[3], rep1 = new int[4], rep2 = new int[4], rep3 = new int[4], rep4 = new int[4];
		try {
			scanner = new Scanner(new File(path));
			while (scanner.hasNextLine()) {
				switch (count) {
					case 0:
						name = scanner.nextLine();
						break;
						
					case 1:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
              System.out.println("i : "+i);
							if((char)i !=' '){
								String s = String.valueOf(i);
								color[cpt]= Integer.parseInt(s);
							}
						}
						break;
						
					case 2:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
							if((char)i !=' '){
								String s = String.valueOf(i);
								rep1[cpt]= Integer.parseInt(s);
							}
						}
						break;
						
					case 3:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
							if((char)i !=' '){
								String s = String.valueOf(i);
								rep2[cpt]= Integer.parseInt(s);
							}
						}
						break;
					
					case 4:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
							if((char)i !=' '){
								String s = String.valueOf(i);
								rep3[cpt]= Integer.parseInt(s);
							}
						}
						break;
						
					case 5:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
							if((char)i !=' '){
								String s = String.valueOf(i);
								rep4[cpt]= Integer.parseInt(s);
							}
						}
						break;
				}
				count++;
				
				//faites ici votre traitement
				
			}
			scanner.close();
			for(i = 0; i < color.length; ++i){
        System.out.println("je veux de la coueleur : "+color[i]);
      }
			return new Shape(name, color, new int[][]{rep1,rep2,rep3,rep4}); 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
  	
  }
  
  public Shape getRandomShape(){
    Random r = new Random();
    int x = Math.abs(r.nextInt()) % shapeModel.length;
    Shape newShape = new Shape( shapeModel[x] );
    instanciedShape.add(newShape);
    return newShape;
  }
  
  public static void main(String[] args){
      ShapesStock ss = new ShapesStock();
      System.out.println(ss.getRandomShape().getName());
      
  }
  
  private static class ShapesStockHolder {

    private static final ShapesStock INSTANCE = new ShapesStock();
  }
}

