
package GameEngine;

public class Brick {
	private char letter;
  private int number;
	
	public Brick(char letter, int number) {
		this.letter = letter;
    this.number = number;
	}
  
  public Brick(Brick b){
    this(b.letter, b.number);
  }
  
  public int getNb(){
    return number;
  }
  
}
