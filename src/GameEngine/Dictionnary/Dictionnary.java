
package GameEngine.Dictionnary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Dictionnary {
  
  HashSet dico;
  
  public Dictionnary() throws IOException {
    dico = new HashSet();
    String file = "./media/dictionnary.txt";
    
    //Read text file
    try{
      InputStream ips = new FileInputStream(file);
      if( ips == null ){
        throw new FileNotFoundException("Can't find the file : "+ file);
      }
      InputStreamReader ipsr = new InputStreamReader(ips, "UTF8");
      BufferedReader br = new BufferedReader(ipsr);
      String line;
      while( (line = br.readLine()) != null ){
        dico.add(line);
      }
      System.out.println(dico.size());
      br.close();
    }catch( FileNotFoundException e ){
      System.out.println( e.getMessage() );
    }catch( IOException e ){
      System.out.println( e.getMessage() );
    }
  }
    
  public boolean included(String s){
    return dico.contains(s);
  }
   
  public static void main( String[] args ) throws IOException{
    Dictionnary d = new Dictionnary();
    String s = "désolé";
    if( d.included(s) ){
      System.out.println("Bravo tu as trouvé un mot du dico");
    }else{
      System.out.println("Non ce mot n'est pas dans le dico");
    }
  }
}
