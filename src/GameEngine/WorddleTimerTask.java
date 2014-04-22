package GameEngine;

import java.util.TimerTask;

public class WorddleTimerTask extends TimerTask{
  private final Player p;
  
  public WorddleTimerTask(Player p){
    this.p = p;
  }

  @Override
  public void run() {
    if(p.isWorddle()){
      p.switchToWorddle(false);
    }else{
      p.setWorddle(true);
    }
  }
  
}
