package GameEngine.Dictionnary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;


public class Dictionnary {

  HashSet dico;

  public Dictionnary() {
    dico = new HashSet();
    String file = "./media/dictionnary.txt";

    //Read text file
    try {
      InputStream ips = new FileInputStream(file);
      if (ips == null) {
        throw new FileNotFoundException("Can't find the file : " + file);
      }
      InputStreamReader ipsr = new InputStreamReader(ips, "UTF8");
      try (BufferedReader br = new BufferedReader(ipsr)) {
        String line;
        while ((line = br.readLine()) != null) {
          dico.add(line);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public boolean included(String s) {
    return dico.contains(s);
  }

  public boolean isAnagramme ( String s){
  	boolean anagramme = true;
		// --- Vérifie que les caractères du mot entré
		// --- sont contenus dans le mot du dictionnaire
		Iterator it = dico.iterator();
		
		while(it.hasNext()){
			anagramme = true;
			int i = 0;
			String str2 = it.next().toString();
			if( s.length() != str2.length() ){
				anagramme = false;
			}
			while ( i < s.length() && anagramme == true ){
				if ( str2.indexOf( s.charAt(i) ) == -1 ){
					anagramme = false;
				}else{
					i++;
				}
			}
			//Idem : on vérifie que les caractères du mot du dictionnaire sont contenus dans le mot entré
			if (anagramme == true){
				System.out.println("mot:" + str2);
			}
			i=0;
			while ( i < str2.length() && anagramme == true ){
				if ( s.indexOf( str2.charAt(i) )  == -1 )
					anagramme = false;
				else i++;
			}
			
			if (anagramme == true){
				System.out.println("Il y a un mot qui correspond:");
				System.out.println(str2);
				return anagramme;
			}
		}
		System.out.println("Pas d'anagramme");
		
		return anagramme;
	}
    
}
