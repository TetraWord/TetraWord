package GameEngine.Dictionnary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

  public String[] findAnagramms(String s) {
	ArrayList<String> list = new ArrayList<String>();
	String[] res = new String[10];
    boolean anagramme = false;
    System.out.println(" s : " + s);

    // --- Vérifie que les caractères du mot entré
    // --- sont contenus dans le mot du dictionnaire
    Iterator it = dico.iterator();

    while (it.hasNext()) {
      anagramme = true;
      int i;
      String str = it.next().toString();

      ArrayList tabCharOfString = new ArrayList<>();
      for (i = 0; i < s.length(); ++i) {
        tabCharOfString.add(s.charAt(i));
      }

      i = 0;

      while (i < str.length() && anagramme == true) {
        char c = str.charAt(i);
        if (s.indexOf(c) == -1) {
          anagramme = false;
        } else {
          if (tabCharOfString.contains(c)) {
            int index = tabCharOfString.indexOf(c);
            tabCharOfString.remove(index);
          } else {
            anagramme = false;
          }
          i++;
        }
      }

      if (anagramme == true) {
    	list.add(str);
    	//res[cpt++] = str2;
        System.out.println("str2 vaut : " + str );
      }

    }

    Collections.sort(list, new Comparator<String>(){
		public int compare(String s1, String s2) {
			if(s1.length() < s2.length()){
				return 1;
			}else if (s1.length() > s2.length()) {
				return -1;
			}
			return 0;
		}
    });
    
    for(int cpt=0; cpt < 10; ++cpt){
    	res[cpt] = list.get(cpt);
    }

    return res;
  }

  public String findBestAnagramm(StringBuilder sb) {
    String bestWord = findAnagramms(sb.toString())[0];
   
    return bestWord;
  }

  public static void main(String[] args) {
    Dictionnary d = new Dictionnary();
    StringBuilder s = new StringBuilder();
    s.append('i');
    s.append('b');
    s.append('t');
    s.append('s');
    s.append('e');
    s.append('f');
    s.append('p');
    s.append('a');
    s.append('l');
    String[] t = d.findAnagramms(s.toString());
    for (int i = 0; i < t.length; ++i) {
     System.out.println("t : " + t[i]);
     }

  }
}
