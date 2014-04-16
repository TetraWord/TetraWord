package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;

public class Hub implements Observer, Observable{
	
  private ArrayList<Observer> listObserver = new ArrayList<>();
	int level;
	InGameState state;
	/*Shape nextShape;*/
	int score;
	/*Time Left*/
	/*Time before mode changing*/
	/*Modifier*/
	
	
	public Hub(){
		this.level = 1;
		this.score = 0;
		this.state = InGameState.TETRIS;
	}

	@Override
	public void update(Observable o, Object args) {
		if ( o instanceof Player ){
			Player p = (Player)o;
			this.level = p.getLevel();
			this.score = p.getScore();
			this.state = p.getState();
			updateObservateur(null);
			System.out.println("Level : " + level);
		}
	}
	
	public int getLevel(){
		return level;
	}
	
	public InGameState getState(){
		return state;
	}

	public int getScore() {
		return score;
	}

	@Override
  public void addObservateur(Observer obs) {
    listObserver.add(obs);
  }

  @Override
  public void updateObservateur(Object args) {
    for (Observer obs : listObserver) {
      obs.update(this, args);
    }
  }

  @Override
  public void delObservateur() {
    listObserver = new ArrayList<>();
  }
}
