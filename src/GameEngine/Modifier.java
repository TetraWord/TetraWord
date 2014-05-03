package GameEngine;

import static GameEngine.Grid.sizeX;
import static GameEngine.Grid.sizeY;
import Pattern.Observable;
import Pattern.Observer;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Modifier implements Observable {
	
	private enum ModifierEnum {
    Speed, Slow, Shake, Storm, Reversal, Exchange, ScorePlus, ScoreMoins, Bomb, TimeTravel, Worddle
  };

  private String name;
  private Timer t = null;
	
	
	public Modifier() {
		Random r = new Random();
		int random = r.nextInt(11);
		this.name = ModifierEnum.values()[random].toString();
	}

  public Modifier(String name) {
		this.name = name;
  }
  
  public Modifier( Modifier m) {
  	this.name = m.getName();
  }

  public String getName() {
    return this.name;
  }

  public void active(Player p) {
    switch (this.name) {
      case "Speed":
        this.changeSpeed(p, '+');
        break;

      case "Slow":
        this.changeSpeed(p, '-');
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
        this.exchange();
        break;

      case "ScorePlus":
        this.score(p, '+');
        break;

      case "ScoreMoins":
        this.score(p, '-');
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

  private void changeSpeed(final Player p, char sign) {
    t = new Timer();

    final int speedBefore = p.getSpeedFall();

    //Set to init speed the player after 5 seconds
    t.schedule(new TimerTask() {
      @Override
      public void run() {
        p.setNewSpeedFall(speedBefore);
      }
    }, 5000);

    //Set the player speed fall to his current speed fall / 2
    if (sign == '+') {
      p.setNewSpeedFall(p.getSpeedFall() / 3);
    } else if (sign == '-') {
      p.setNewSpeedFall(p.getSpeedFall() * 3);
    }

  }

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
				},0 , 25);
						
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						int random = (int) (Math.random() * 3);
						switch(random){
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
				},0 , 500);
				
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

  private void reversal(Player p) {

  }

  private void exchange() {
    GameEngine g = GameEngine.getInstance();
    if (g.getNbPlayers() == 2) {
      g.exchange();
    }
  }

  private void score(Player p, char sign) {
    if (sign == '+') {
      p.addToScore(500);
    } else if (sign == '-') {
      p.addToScore(-500);
    }
  }

  private void bomb(Player p) {
    Grid grid = p.getBoardGame().getGrid();
    Brick[][] tGrid = grid.getTGrid();
    CurrentShape s = grid.getCurrentShape();
    //Random beetween 1 and 2;
    int radius = (int) Math.random() + 1;

    int[][] representation = s.getRepresentation();
    int x = s.getX();
    int y = s.getY();
    int maxX = s.getMaxX(representation);
    int maxY = s.getMaxY(representation);

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
    grid.updateObservateur(tGrid);
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
