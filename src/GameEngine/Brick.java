package GameEngine;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <b> Brick is a logical part of the game representing the Bricks of a
 * shape.</b>
 * <p>
 * A Brick contains:
 * <ul>
 * <li> A letter for the Worddle/Anagram mode </li>
 * <li> A number: 1 for visible, else -1 </li>
 * <li> A Color </li>
 * <li> Boolean to know if the Brick is clicked </li>
 * <li> Boolean to know if the Brick is double clicked </li>
 * <li> Boolean to know if the Brick is selected and in a right word in a
 * Worddle mode </li>
 * <li> Boolean to know if the Brick can be selected in a new word in a Worddle
 * mode </li>
 * <li> An array of char to know the letter's appearance frequency </li>
 * </ul>
 * </p>
 *
 * @see: Shape
 */
public class Brick {

  /**
   * The Brick's letter.
   *
   * @see Brick#getLetter()
   */
  private char letter;

  /**
   * The Shape's number.
   *
   * @see Brick#getNb()
   */
  private int number;

  /**
   * The Brick's color.
   *
   * @see Brick#getColor()
   */
  private Color color;

  /**
   * Allow to know if the Brick is clicked.
   *
   * @see Brick#isClicked()
   * @see Brick#setClicked(boolean)
   */
  private boolean clicked = false;

  /**
   * Allow to know if the Brick is double clicked.
   *
   * @see Brick#isDoubleClicked()
   * @see Brick#setClicked(boolean)
   */
  private boolean doubleClicked = false;

  /**
   * Allow to know if the Brick is gonna be destroyed.
   *
   * @see Brick#gonnaBeDestroyed()
   * @see Brick#setToDestroy()
   */
  private boolean gonnaBeDestroyed = false;

  /**
   * Allow to know if the Brick can be in a new word in Worddle mode.
   *
   * @see Brick#setNewClickable()
   * @see Brick#canBeInANewWord()
   */
  private boolean newClickable = false;

  /**
   * Array of char to know the frequency letter in french language.
   */
  private static char[] frequencyLetter = initLetters();

  /**
   * It's the main constructor for Brick.
   *
   * @param letter char
   * @param number int
   * @param c Color
   */
  private Brick(char letter, int number, Color c) {
    this.letter = letter;
    this.number = number;
    this.color = c;
  }

  /**
   * Call the main constructor with the letter and number get in parameters and
   * white color.
   *
   * @param letter char
   * @param number int
   */
  public Brick(char letter, int number) {
    this(letter, number, new Color(255));
  }

  /**
   * Call the main constructor with the color and number get in parameters. Then
   * it give an aleatory letter.
   *
   * @param number int
   * @param c Color
   */
  public Brick(int number, Color c) {
    this(' ', number, c);
    this.letter = getAleaLetter();
  }

  /**
   * The copy constructor call the main constructor with the parameters of the
   * Brick is copied.
   *
   * @param b Brick
   */
  public Brick(Brick b) {
    this(b.letter, b.number, b.color);
  }

  /**
   *
   * @return an aleatory char
   */
  private char getAleaLetter() {
    int lower = 0;
    int higher = frequencyLetter.length;
    int random = (int) (Math.random() * (higher - lower)) + lower;
    return frequencyLetter[random];
  }

  /**
   * Allow to know the letters's appearance frequency.
   *
   * @return an array of char
   */
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

  /**
   * Get the number contained in the Brick.
   *
   * @return the number contained in the Brick
   */
  public int getNb() {
    return number;
  }

  /**
   * Get the color of the Brick.
   *
   * @return the color of the Brick
   */
  public Color getColor() {
    return color;
  }

  /**
   * Get the letter of the Brick.
   *
   * @return the letter of the Brick
   */
  public char getLetter() {
    return letter;
  }

  /**
   * Select the Brick for Worddle mode to destroy it.
   */
  public void setToDestroy() {
    gonnaBeDestroyed = true;
  }

  /**
   * Allow to know if the Brick is selected
   *
   *
   * @return
   */
  public boolean gonnaBeDestroyed() {
    return gonnaBeDestroyed;
  }

  /**
   * Verify if it's clicked or not. If it's clicked, verify if it's a simple or
   * a double clicked.
   *
   * @param b: Boolean represents it's clicked
   */
  public void setClicked(boolean b) {
    if (clicked == true && b) {
      doubleClicked = true;
    } else if (!b) {
      doubleClicked = false;
    }
    clicked = b;
  }

  /**
   * Return true if it's clicked, else false.
   *
   * @return the boolean if it's clicked
   */
  public boolean isClicked() {
    return clicked;
  }

  /**
   * Return true if it's double clicked, else false.
   *
   * @return the boolean if it's double clicked
   */
  public boolean isDoubleClicked() {
    return doubleClicked;
  }

  /**
   * Set the Brick clickable for a new word in Worddle mode.
   *
   * @param b Boolean
   */
  void setNewClickable(Boolean b) {
    this.newClickable = b;
  }

  /**
   * @return True if it can be in a new word in Worddle mode, else false.
   */
  public boolean canBeInANewWord() {
    return this.newClickable;
  }

}
