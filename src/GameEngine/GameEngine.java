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

  public Brick[][][] getBrickGrids() {
    Brick[][][] tabBrick = new Brick[nbPlayer][Grid.sizeY][Grid.sizeX];
    for(int i = 0; i < nbPlayer; ++i){
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
}
