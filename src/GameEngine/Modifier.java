package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class Modifier implements Observable {

  public String name;
  public int timer;
  public Timer t = null;

  public Modifier(String name) {
    this.name = name;
    this.timer = 10;
  }

  public String getName() {
    return this.name;
  }

  public void setTimer(int timer) {
    this.timer = timer;
  }

  public void active(Player p) {
    switch (this.name) {
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

  private void speed(final Player p) {
    t = new Timer();

    final int speedBefore = p.getSpeedFall();

    //Set to init speed the player after 5 seconds
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        p.setNewSpeedFall(speedBefore);
      }
    }, 5000);

    //Set the player speed fall to his current speed fall * 2
    p.setNewSpeedFall(p.getSpeedFall() * 2);
  }

  private void shake(Player p) {

  }

  private void storm(Player p) {
    while (timer != 0) {
      p.right();
      timer--;
    }
  }

  private void reversal(Player p) {

  }

  private void exchange(Player p) {

  }

  private void score(Player p) {

  }

  private void bomb(Player p) {

  }

  private void timeTravel(Player p) {

  }

  private void worddle(Player p) {
    if (!GameEngine.getInstance().isPlayersInWordMode() && p.canWorddle()) {
      p.switchToWorddle(true);
      GameEngine.getInstance().beginWorddleTimer(p);
      p.stockCurrentShape();
      p.addNewChar(p.getBoardGame().clickedOneBrick());
    }
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
