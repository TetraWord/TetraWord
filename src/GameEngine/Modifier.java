package GameEngine;

import Pattern.Observable;
import Pattern.Observer;

public class Modifier implements Observable {
	
	public String name;
	public int timer;
	
	public Modifier(String name){
		this.name = name;
		this.timer = 10;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void active(Player p){
		switch(this.name){
			case "Speed":
				this.speed(p);
				break;
				
			case "Shake":
				this.shake(p);
				break;
				
			case "Storm":
				this.storm(p);
				break;
				
			case "Reversal":
				this.reversal(p);
				break;
				
			case "Exchange":
				this.exchange(p);
				break;
				
			case "Score":
				this.score(p);
				break;
				
			case "Bomb":
				this.bomb(p);
				break;
			
			case "TimeTravel":
				this.timeTravel(p);
				break;
				
			case "Worddle":
				this.worddle(p);
				break;
		}
	}
	
	public void speed(Player p){
		while(timer !=0 ) {
			p.down(3);
			timer--;
		}
	}
	
	public void shake(Player p){
		
	}
	
	public void storm(Player p){
		while(timer !=0 ) {
			p.right();
			timer--;
		}
	}
	
	public void reversal(Player p){
		
	}
	
	public void exchange(Player p){
		
	}
	
	public void score(Player p){
		
	}
	
	public void bomb(Player p){
		
	}
	
	public void timeTravel(Player p){
		
	}
	
	public void worddle(Player p){
		p.switchToWorddle(true);
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
