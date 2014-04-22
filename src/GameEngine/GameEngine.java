package GameEngine;

import GameEngine.Dictionnary.Dictionnary;
import java.util.Timer;

public class GameEngine {

  private static final GameEngine INSTANCE = new GameEngine();
  private final Player[] players;
  private int nbPlayer = 0;
  private final Dictionnary dictionnary = new Dictionnary();
  private Timer worddleTimer = null;

  //Singleton
  private GameEngine() {
    players = new Player[4];
  }

  public static GameEngine getInstance() {
    return INSTANCE;
  }

  public void addNewPlayer(Shape s, Shape s2) {
    players[nbPlayer] = new Player(nbPlayer, s, s2, dictionnary);
    ++nbPlayer;
  }

  public Player[] getPlayers() {
    return players;
  }

  public int getNbPlayers() {
    return nbPlayer;
  }
  
  public void beginWorddleTimer(Player p) {
    worddleTimer = new Timer();
    worddleTimer.schedule(new WorddleTimerTask((p)), 30000);
  }

  public void finishTimerWorddle(){
    worddleTimer = null;
  }
  
  public boolean timerWorddleIsAlive() {
    return worddleTimer != null;
  }

  public boolean isPlayersInWordMode() {
    for(int i = 0; i < nbPlayer; ++i ){
      if(!players[i].isTetris()){
        return true;
      }
    }
    return false;

  }
}
