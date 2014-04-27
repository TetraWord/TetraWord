package GameEngine;

import ContextManager.ContextManager;
import GameEngine.Dictionnary.Dictionnary;
import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {

  private static final GameEngine INSTANCE = new GameEngine();
  private final Player[] players;
  private int nbPlayer = 0;
  private final Dictionnary dictionnary = new Dictionnary();
  private boolean ia = false;
  private Timer worddleTimer = null;
  private Timer gameTimer = null;
  private long tBegin;
  //10 minutes
  private final long timeToEndGame = 10 * 60 * 1000;

  //Singleton
  private GameEngine() {
    players = new Player[4];
  }

  public static GameEngine getInstance() {
    return INSTANCE;
  }

  public void addNewPlayer(Shape s, Shape s2, boolean isIA) {
    if( isIA ){
      players[nbPlayer] = new IA(nbPlayer, s, s2, dictionnary);
    } else {
      players[nbPlayer] = new Player(nbPlayer, s, s2, dictionnary);
    }
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

  public void finishTimerWorddle() {
    worddleTimer = null;
  }

  public boolean timerWorddleIsAlive() {
    return worddleTimer != null;
  }

  public boolean isPlayersInWordMode() {
    for (int i = 0; i < nbPlayer; ++i) {
      if (!players[i].isTetris()) {
        return true;
      }
    }
    return false;

  }

  public Brick[][][] getBrickGrids() {
    Brick[][][] tabBrick = new Brick[nbPlayer][Grid.sizeY][Grid.sizeX];
    for (int i = 0; i < nbPlayer; ++i) {
      tabBrick[i] = players[i].getBoardGame().getGrid().getTGrid();
    }
    return tabBrick;
  }

  public void exchange() {
    Player p1 = players[0];
    Player p2 = players[1];

    Grid p1Grid = p1.getBoardGame().getGrid();
    Grid p2Grid = p2.getBoardGame().getGrid();

    CurrentShape p1CS = p1Grid.getCurrentShape();
    CurrentShape p2CS = p2Grid.getCurrentShape();

    p1Grid.setCurrentShape(p2CS);
    p2Grid.setCurrentShape(p1CS);

    Brick[][] p1TGrid = p1Grid.getTGrid();
    Brick[][] p2TGrid = p2Grid.getTGrid();

    Brick[][] tmp = (Brick[][]) p1TGrid.clone();
    p1Grid.setTGrid(p2TGrid);
    p2Grid.setTGrid(tmp);

    p1Grid.updateObservateur(p2CS);
    p2Grid.updateObservateur(p1CS);
    p1Grid.updateObservateur(p2TGrid);
    p2Grid.updateObservateur(p1TGrid);

  }

  public boolean hasIA() {
    return ia;
  }

  public void setIA(boolean ia) {
    this.ia = ia;
  }
  
  public void stop() {
    //TO DO
  }

  public void setGameTimer() {
    gameTimer = new Timer();
    tBegin = System.nanoTime();
    gameTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        ContextManager.getInstance().stop();
      }
    }, timeToEndGame);
  }
  
  public long getTimeLeft() {
    return ((timeToEndGame * 1000000 - (System.nanoTime() - tBegin)) / 1000000000);
  }
}
