package GameEngine;

import java.awt.Color;
import java.util.Random;

public class Brick {

  private char letter;
  private int number;
  private Color color;

  public Brick(char letter, int number, Color c) {
    this.letter = letter;
    this.number = number;
    this.color = c;
  }

  public Brick(char letter, int number) {
    this(letter, number, new Color(255));
  }

  public Brick(int number, Color c) {
    Random r = new Random();
    this.number = number;
    this.color = c;
    char l = (char) ((int) 'a' + r.nextInt(26));

    System.out.println("C vaut :" + l);

  }

  public Brick(Brick b) {
    this(b.letter, b.number, b.color);
  }

  public int getNb() {
    return number;
  }

  public Color getColor() {
    return color;
  }
	
  public char getLetter(){
    return letter;
  }
  
}
