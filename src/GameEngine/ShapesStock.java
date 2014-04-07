
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
  	char c;
  	StringReader l;
  	StringBuilder res;
  	String name ="", s;
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
						res = new StringBuilder();
						while((i=l.read()) != -1 ) {
							if((c=(char)i) !=' '){
								res.append(c);
							}else{
								s = String.valueOf(res.toString());
								color[cpt]= Integer.parseInt(s);
								res = new StringBuilder();
								cpt++;
							}
						}
						s = String.valueOf(res.toString());
						color[cpt]= Integer.parseInt(s);
						l.close();
						break;
						
					case 2:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
							if((c=(char)i) !=' '){
								s = String.valueOf(c);
								rep1[cpt]= Integer.parseInt(s);
								cpt++;
							}
						}
						l.close();
						break;
						
					case 3:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
							if((c=(char)i) !=' '){
								s = String.valueOf(c);
								rep2[cpt]= Integer.parseInt(s);
								cpt++;
							}
						}
						l.close();
						break;
					
					case 4:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
							if((c=(char)i) !=' '){
								s = String.valueOf(c);
								rep3[cpt]= Integer.parseInt(s);
								cpt++;
							}
						}
						l.close();
						break;
						
					case 5:
						l = new StringReader(scanner.nextLine());
						cpt = 0;
						while((i=l.read()) != -1 ) {
							if((c=(char)i) !=' '){
								s = String.valueOf(c);
								rep4[cpt]= Integer.parseInt(s);
								cpt++;
							}
						}
						l.close();
						break;
				}
				count++;
				
			}
			scanner.close();
			for(i = 0; i < color.length; ++i){
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
      
  }
  
  private static class ShapesStockHolder {

    private static final ShapesStock INSTANCE = new ShapesStock();
  }
}

