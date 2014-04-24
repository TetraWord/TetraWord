package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Hub implements Observer, Observable {

  private ArrayList<Observer> listObserver = new ArrayList<>();
  private final Queue<String> listMessage = new LinkedList<>();
  private InGameState state;
  private int level;
  private String word;
  private Shape nextShape;
  private int score;
	private int nbRemovedLine;
	private boolean wordFinish;
  /*Time Left*/
  private int secondBeforeWorddle;
  /*Modifier*/

  public Hub() {
    this.level = 1;
    this.score = 0;
    this.secondBeforeWorddle = 30;
    this.state = InGameState.TETRIS;
		this.nbRemovedLine = 0;
		this.wordFinish = true;
  }

  public int getLevel() {
    return level;
  }

  public InGameState getState() {
    return state;
  }

  public int getScore() {
    return score;
  }

  public String getWord() {
    return word;
  }
  
	public int getNbLines() {
		return nbRemovedLine;
	}

  public int getTimeBeforeWorddle() {
    return secondBeforeWorddle;
  }

	public Shape getNextShape() {
		return nextShape;
	}
	
	public String getOlderMessage() {
		return listMessage.poll();
	}
	
	public boolean isWordFinish() {
		return wordFinish;
	}
	
  @Override
  public void update(Observable o, Object args) {
    if (o instanceof Player) {
      Player p = (Player) o;
      this.level = p.getLevel();
      this.score = p.getScore();
      this.state = p.getState();
			this.nbRemovedLine = p.getNbLines();
      this.word = p.getWord();
      double seconds = (double) p.getTime() / 1000000000.0;
      this.secondBeforeWorddle = 30 - (int) Math.round(seconds);
			if(p.isWordFinished()){
				this.word = null;
			}else{
				this.word = p.getWord();
			}
			this.wordFinish = p.isWordFinished();
      updateObservateur(null);
    }
		if (o instanceof BoardGame){
			if (args == null){
				this.nextShape = ((BoardGame)o).getNextShape();
				updateObservateur(null);
			} else {
				if(args instanceof String){
					listMessage.add(((String)args));
					updateObservateur(null);
				}
			}
		}
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
