package GameEngine;

import Pattern.Observable;
import Pattern.Observer;

public class Modifier implements Observable {
	
	public enum modifierEnum {
		Speed, Shake, Storm, Reversal, Exchange, Score, Bomb, TimeTravel, Worddle 
	};
	public String name;
	
	public Modifier(String name){
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void active(){
		switch(this.name){
			case "Speed":
				this.speed();
				break;
				
			case "Shake":
				this.shake();
				break;
				
			case "Storm":
				this.storm();
				break;
				
			case "Reversal":
				this.reversal();
				break;
				
			case "Exchange":
				this.exchange();
				break;
				
			case "Score":
				this.score();
				break;
				
			case "Bomb":
				this.bomb();
				break;
			
			case "TimeTravel":
				this.timeTravel();
				break;
				
			case "Worddle":
				this.worddle();
				break;
		}
	}
	
	public void speed(){
		
	}
	
	public void shake(){
		
	}
	
	public void storm(){
		
	}
	
	public void reversal(){
		
	}
	
	public void exchange(){
		
	}
	
	public void score(){
		
	}
	
	public void bomb(){
		
	}
	
	public void timeTravel(){
		
	}
	
	public void worddle(){
		
	}

	@Override
	public void addObservateur(Observer obs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObservateur(Object args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delObservateur() {
		// TODO Auto-generated method stub
		
	}
}
