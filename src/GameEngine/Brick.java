package GameEngine;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Brick {

  private char letter;
  private int number;
  private Color color;
  private boolean clicked = false;
  private boolean doubleClicked = false;
  private boolean isInWord = false;
  private static char[] frequencyLetter = initLetters();

  private Brick(char letter, int number, Color c) {
    this.letter = letter;
    this.number = number;
    this.color = c;
  }

  public Brick(char letter, int number) {
    this(letter, number, new Color(255));
  }

  public Brick(int number, Color c) {
    this.number = number;
    this.color = c;
    this.letter = getAleaLetter();
  }

  public Brick(Brick b) {
    this(b.letter, b.number, b.color);
  }

  private char getAleaLetter() {
    int lower = 0;
    int higher = frequencyLetter.length;
    int random = (int) (Math.random() * (higher - lower)) + lower;
    return frequencyLetter[random];
  }

  private static char[] initLetters() {
    char[] letters;
    HashMap<Character, Integer> occurence = new HashMap<>();
    char c;
    Scanner scanner;
    String[] words;
    /* Read file to save occurence */
    try {
      scanner = new Scanner(new File("./media/lettersFrequency.txt"));
      while (scanner.hasNextLine()) {
        String l = scanner.nextLine();
        words = l.split(" ");
        occurence.put(words[0].charAt(0), Integer.parseInt(words[1]));
      }

      /* Count number of letter for the size of the letters table*/
      int size = 0;
      Collection<Integer> collec = occurence.values();
      for (int nb : collec) {
        size = size + nb;
      }
      letters = new char[size];

      /* Construct table with right number of occurence */
      int i = 0;
      for (int l = 0; l < occurence.size(); ++l) {
        for (int nb = 0; nb < occurence.get((char) (l + 97)); ++nb) {
          letters[i] = (char) (l + 97);
          ++i;
        }

      }

      return letters;

    } catch (FileNotFoundException ex) {
      Logger.getLogger(Brick.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;

  }

  public int getNb() {
    return number;
  }

  public Color getColor() {
    return color;
  }

  public char getLetter() {
    return letter;
  }

  public void setInWord() {
    isInWord = true;
  }
  
  public boolean isInWord() {
    return isInWord;
  }

  public void setClicked(boolean b) {
    if (clicked == true) {
      doubleClicked = true;
    }
    clicked = b;
  }

  public boolean isClicked() {
    return clicked;
  }

  public boolean isDoubleClicked() {
    return doubleClicked;
  }

}
