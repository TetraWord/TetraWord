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

  /**
   * Look if the string is a word contains in the dictionary
   * 
   * @param s
   * @return true if the String is included in the dictionary
   */
  public boolean included(String s) {
    return dico.contains(s);
  }

  /**
   * Search possible anagrams with the chars of String in the dictionary  
   * 
   * @param s: String formed by chars selected
   * @return Array of 10 Strings : the first is the best one, the last isn't an anagram
   *
   */
  public String[] findAnagramms(String s) {
		ArrayList<String> list = new ArrayList<String>();
		String[] res = new String[10];
    boolean anagramme = false;

    // --- Verify that the chars of the word entered are contained in a word of the dictionary
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

      //if the word is an anagram, add in the list of anagram
      if (anagramme == true) {
      	list.add(str);
      }
    }

    //Sort words by length
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
    

    int cpt=1;
    for(int i=1; i<list.size() ; i=i+10){
	    if(list.get(i).length() > 2 && cpt < 9){
	  		res[cpt++] = list.get(i);
	  	}
    }
    
    //The first word is the best and the last isn't an anagram
    res[0] = list.get(0);
    res[9] = s.toString();

    return res;
  }

  /**
   * Call findBestAnagramm to getting the best one
   * 
   * @param sb: StringBuilder contains chars selected to form a word
   * @return the best anagram find in a String
   */
  public String findBestAnagramm(StringBuilder sb) {
    return findAnagramms(sb.toString())[0];
  }
}
