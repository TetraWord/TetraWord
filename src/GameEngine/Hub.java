package GameEngine;

import Pattern.Observable;
import Pattern.Observer;

public class Hub implements Observer{
	
	/*Contient son player ou observe son player ?*/
	int level;
	/*Shape nextShape;*/
	int score;
	/*Time Left*/
	/*Time before mode changing*/
	/*Modifier*/
	
	
	public Hub(){
		this.level = 1;
		this.score = 0;
	}

	@Override
	public void update(Observable o, Object args) {
		if ( o instanceof Player ){
			Player p = (Player)o;
			this.level = p.getLevel();
			this.score = p.getScore();
		}
	}
	
	public int getLevel(){
		return level;
	}
}
