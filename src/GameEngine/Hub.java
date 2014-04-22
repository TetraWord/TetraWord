package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.ArrayList;

public class Hub implements Observer, Observable {

  private ArrayList<Observer> listObserver = new ArrayList<>();
  private int level;
  private InGameState state;
  private String word;
  /*Shape nextShape;*/
  private int score;
  /*Time Left*/
  private int secondBeforeWorddle;
  /*Modifier*/

  public Hub() {
    this.level = 1;
    this.score = 0;
    this.secondBeforeWorddle = 30;
    this.state = InGameState.TETRIS;
  }

  public int getLevel() {
    return level;
  }

  public InGameState getState() {
    return state;
  }

  @Override
  public void update(Observable o, Object args) {
    if (o instanceof Player) {
      Player p = (Player) o;
      this.level = p.getLevel();
      this.score = p.getScore();
      this.state = p.getState();
      this.word = p.getWord();
      double seconds = (double)p.getTime() / 1000000000.0;
      this.secondBeforeWorddle = 30 - (int)Math.round(seconds);
      updateObservateur(null);
    }
  }

  public int getScore() {
    return score;
  }

  public String getWord() {
    return word;
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

  public String getTimeBeforeWorddle() {
    return Integer.toString(secondBeforeWorddle);
  }
}
