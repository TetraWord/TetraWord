package GameEngine;

public class GameEngine {

  private GameState currentState;
  private static final GameEngine INSTANCE = new GameEngine();
  private final Player[] players;
  private int nbPlayer = 0;
  
  //Singleton
  private GameEngine(){
    currentState = GameState.NO_STATE;
    players = new Player[4];
  }
  
  public static GameEngine getInstance(){
    return INSTANCE;
  }
  
  public void init(){
    currentState = GameState.IN_MENU;
  }
  
  public void update(){
    
  }
  
  public void addNewPlayer(Shape s){
    players[nbPlayer] = new Player(nbPlayer, s);
    ++nbPlayer;
  }
  
  public Player[] getPlayers(){
    return players;
  }

  public int getNbPlayers(){
    return nbPlayer;
  }
  
  public GameState getState() {
    return currentState;
  }
  
  public void setState(GameState newState){
    currentState = newState;
  }

}