package GameEngine;

import static GameEngine.Grid.sizeX;
import static GameEngine.Grid.sizeY;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <b> Modifier is a logical part of the game representing the Modifier. </b>
 * <p>
 * A Modifier contains:
 * <ul>
 * <li> modifierSingleEnum: An enum of the different  existing for
 * single Player only. </li>
 * <li> modifierMultiEnum: An enum of the different  existing for
 * multiplayer. </li>
 * <li> name: A string for the name of the Modifier. </li>
 * <li> t: A Timer </li>
 * </ul>
 * </p>
 */
public class Modifier {

  private enum modifierSingleEnum {

    Speed, Shake, Storm, Score, Bomb
  };

  private enum modifierMultiEnum {

    Speed, Shake, Storm, Exchange, Score, Bomb, Worddle
  }

  /*Non implémenté
   Reversal
   TimeTravel
   */
  private final String name;
  private Timer t = null;

  /**
   * Modifier constructor. Create a random  among modifierEnum.
   */
  public Modifier() {
    int random;
    if (GameEngine.getInstance().getNbPlayers() == 1) {
      random = (int) (Math.random() * 6);
      this.name = modifierSingleEnum.values()[random].toString();
    } else {
      random = (int) (Math.random() * 7);
      this.name = modifierMultiEnum.values()[random].toString();
    }
  }

  /**
   * Modifier constructor by copy.
   *
   * @param m Modifier to copy
   */
  public Modifier(Modifier m) {
    this.name = m.getName();
  }

  /**
   * Get the name of the .
   *
   * @return The name of the 
   */
  public String getName() {
    return this.name;
  }

  /**
   * Activate the .
   * <p>
   * Call the function corresponding of the 's name. </p>
   *
   * @param p Player for whom the  is activated
   */
  public void active(Player p) {
    int random;
    Player pAffect;
    GameEngine g = GameEngine.getInstance();
    if (g.getNbPlayers() > 1) {
      Player[] ps = g.getPlayers();
      if (p.getNumber() == 1) {
        pAffect = ps[2];
      } else {
        pAffect = ps[1];
      }
    } else {
      pAffect = p;
    }

    switch (this.name) {
      case "Speed":
        random = (int) Math.random();
        if (random == 0) {
          this.changeSpeed(pAffect, '+');
        } else {
          this.changeSpeed(p, '-');
        }
        break;

      case "Shake":
        this.shake(pAffect);
        break;

      case "Storm":
        this.storm(pAffect);
        break;

      case "Reversal":
        this.reversal(pAffect);
        break;

      case "Exchange":
        this.exchange();
        break;

      case "Score":
        random = (int) Math.random();
        if (random == 0) {
          this.score(p, '+');
        } else {
          this.score(pAffect, '-');
        }
        break;

      case "Bomb":
        this.bomb(p);
        break;

      case "TimeTravel":
        this.timeTravel(pAffect);
        break;

      case "Worddle":
        this.worddle(pAffect);
        break;
    }
  }

  /**
   * Change the speed of the game.
   *
   * @param p Player for whom the  is activated
   * @param sign Represent if the speed raise or decreases
   */
  private void changeSpeed(final Player p, char sign) {
    t = new Timer();

    final int speedBefore = p.getSpeedFall();

    //Set to init speed the Player after 5 seconds
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        p.setNewSpeedFall(speedBefore);
      }
    }, 5000);

    //Set the Player speed fall to his current speed fall / 2
    if (sign == '+') {
      p.setNewSpeedFall(p.getSpeedFall() / 3);
    } else if (sign == '-') {
      p.setNewSpeedFall(p.getSpeedFall() * 3);
    }

  }

  /**
   * Shake the grid.
   *
   * @param p Player for whom the  is activated
   */
  private void shake(final Player p) {

    final int lower = -10;
    final int higher = 10;

    t = new Timer();

    t.schedule(new TimerTask() {
      @Override
      public void run() {
        int random = (int) (Math.random() * (higher - lower)) + lower;
        int offsetX = random;
        random = (int) (Math.random() * (higher - lower)) + lower;
        int offsetY = random;
        p.shake(offsetX, offsetY);
      }
    }, 0, 25);

    t.schedule(new TimerTask() {
      @Override
      public void run() {
        int random = (int) (Math.random() * 3);
        switch (random) {
          case 0:
            p.left();
            break;
          case 1:
            p.right();
            break;
          default:
            break;
        }
      }
    }, 0, 500);

    //Stop after 5 secondes 
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        p.shake(0, 0);
        t.purge();
        t.cancel();
      }
    }, 10000);

  }

  /**
   * Move the shape to the left or the right.
   *
   * @param p Player for whom the  is activated
   */
  private void storm(final Player p) {
    t = new Timer();

    final int r = (int) Math.round(Math.random());

    //Lateral movement every 0.7 seconds 
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        if (r == 0) {
          p.left();
        } else {
          p.right();
        }
      }
    }, 0, 700);

    //Stop after 10 secondes 
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        t.purge();
        t.cancel();
      }
    }, 10000);
  }

  /**
   * Reverse the board game ; the current shape come from the down and go up.
   *
   * @param p Player for whom the  is activated
   */
  private void reversal(Player p) {

  }

  /**
   * Exchange the boardGame between the two Players.
   */
  private void exchange() {
    GameEngine g = GameEngine.getInstance();
    if (!g.isPlayersInWordMode()) {
      g.exchange();
    }
  }

  /**
   * Change the score ; add a bonus or remove a malus.
   *
   * @param p Player for whom the  is activated
   * @param sign To know if it's a bonus or a malus
   */
  private void score(Player p, char sign) {
    if (sign == '+') {
      p.addToScore(500);
    } else if (sign == '-') {
      p.addToScore(-500);
    }
  }

  /**
   * Burst the current shape and the shapes around.
   *
   * @param p Player for whom the  is activated
   */
  private void bomb(Player p) {
    Grid grid = p.getGrid();
    Brick[][] tGrid = grid.getTGrid();
    CurrentShape s = grid.getCurrentShape();
    //Random beetween 1 and 2;
    int radius = (int) Math.random() + 1;

    int[][] representation = s.getRepresentation();
    int x = s.getX();
    int y = s.getY();
    int maxX = s.getMaxWidth(representation);
    int maxY = s.getMaxHeight(representation);

    for (int i = y; i <= y + maxY; ++i) {
      for (int j = x; j <= x + maxX; ++j) {
        if (i + radius < sizeY) {
          if (j + radius < sizeX) {
            tGrid[i + radius][j + radius] = new Brick(' ', -1);
          }
          if (j - radius >= 0) {
            tGrid[i + radius][j - radius] = new Brick(' ', -1);
          }
        }
        if (i - radius >= 0) {
          if (j + radius < sizeX) {
            tGrid[i - radius][j + radius] = new Brick(' ', -1);
          }
          if (j - radius >= 0) {
            tGrid[i - radius][j - radius] = new Brick(' ', -1);
          }
        }
      }
    }

    p.getBoardGame().launchNextShape();
    grid.setTGrid(tGrid);
    grid.updateObserver(tGrid);
  }

  /**
   * Go 30 seconds back.
   *
   * @param p Player for whom the  is activated
   */
  private void timeTravel(Player p) {

  }

  /**
   * Go to worddle mode.
   *
   * @param p Player for whom the  is activated
   */
  private void worddle(Player p) {
    if (!GameEngine.getInstance().isPlayersInWordMode() && p.canWorddle()) {
      p.switchToWorddle(true);
      GameEngine.getInstance().beginWorddleTimer(p);
      p.stockCurrentShape();
      p.addNewChar(p.getGrid().clickedOneBrick());
    }
  }

}