package GameEngine;

public class GameEngine {

  private static final GameEngine INSTANCE = new GameEngine();
  private final Player[] players;
  private int nbPlayer = 0;

  //Singleton
  private GameEngine() {
    players = new Player[4];
  }

  public static GameEngine getInstance() {
    return INSTANCE;
  }

  public void addNewPlayer(Shape s, Shape s2) {
    players[nbPlayer] = new Player(nbPlayer, s, s2);
    ++nbPlayer;
  }

  public Player[] getPlayers() {
    return players;
  }

  public int getNbPlayers() {
    return nbPlayer;
  }
}
