package GameEngine;

import ContextManager.ContextManager;
import GameEngine.Dictionnary.Dictionnary;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IA extends Player implements Runnable {

  private int bestTranslationDelta;
  private int bestRotationDelta;
  private int bestMerit;

  public IA(int nb, Shape s, Shape s2, Dictionnary d) {
    super(nb, s, s2, d);
  }

  @Override
  public void doAnagram() {
    Grid g = boardGame.getGrid();
    int numLinesRemoved = 0;
    while (g.getFirstFullLine() != -1) {
      StringBuilder letters = getGrid().getAllLetterFromTheRemovedLine();
      String[] bestWords = getDico().findAnagramms(letters.toString());
      String bestWord;
      try {
        Thread.sleep(3000);
      } catch (InterruptedException ex) {
        Logger.getLogger(IA.class.getName()).log(Level.SEVERE, null, ex);
      }
      int r = (int) Math.random() * 100;
      if (r < 70) {
        //Select the bestWord
        System.out.println("je prend le meilleur");
        bestWord = bestWords[0];
      } else if (r > 70 && r < 95) {
        System.out.println("je prend dedans au hasard");
        bestWord = bestWords[(int) Math.random() * bestWords.length - 2];
      } else {
        System.out.println("je prend un mauvais ");
        bestWord = bestWords[bestWords.length - 1];
      }
      for (int i = 0; i < bestWord.length(); ++i) {
        g.selectBrick(g.getFirstFullLine(), bestWord.charAt(i));
        addNewChar(bestWord.charAt(i));
        try {
          Thread.sleep(700);
        } catch (InterruptedException ex) {
          Logger.getLogger(IA.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      try {
        Thread.sleep(1200);
      } catch (InterruptedException ex) {
        Logger.getLogger(IA.class.getName()).log(Level.SEVERE, null, ex);
      }
      verifAnagram(bestWord);
      clearWord();
      getGrid().removeLine(getGrid().getFirstFullLine());
      ++numLinesRemoved;
    }

    finishAnagram(numLinesRemoved);
  }

  @Override
  public void doWorddle() {
    while (GameEngine.getInstance().timerWorddleIsAlive()) {
      
      String s = getWord();
      if (getDico().included(s)) {
        updateObservateur("Mot valide !");
        boardGame.getGrid().setBricksToDestroy();
        addToScore(s.length() * 3);
      } else {
        System.out.println("Non Existant");
        boardGame.getGrid().clearTabBrickClicked();
        addToScore(-s.length() * 4);
      }
      clearWord();
      boardGame.getGrid().setAllowDoubleClick(true);
      updateObservateur(null);
    }
    finishWorddle();
  }

  public int[] gestBestMoveShape() {

    CurrentShape s = getCurrentShape();
    Grid g = boardGame.getGrid();
    int width = Grid.sizeX;
    int height = Grid.sizeY;

    int currentBestTranslationDelta = 0;
    int currentBestRotationDelta = 0;
    double currentBestMerit = (-1.0e20);
    int currentBestPriority = 0;

    int trialTranslationDelta = 0;
    int trialRotationDelta = 0;
    double trialMerit = 0.0;
    int trialPriority = 0;

    int maxOrientations = 0;
    int moveAcceptable = 0;

    CurrentShape tmpShape;
    Grid tmpGrid;

    maxOrientations = 4;

    for (trialRotationDelta = 0; trialRotationDelta < maxOrientations; ++trialRotationDelta) {
      // Make temporary copy of piece, and rotate the copy.
      tmpShape = new CurrentShape(s);

      for (int count = 0; count < trialRotationDelta; ++count) {
        tmpShape.rotateLeft(g.getTGrid());
      }

      // Determine the translation limits for this rotated piece.
      int moveIsPossible = 0;
      int minDeltaX = 0;
      int maxDeltaX = 0;

      int[] result = g.DetermineAccessibleTranslationsForPieceOrientation(tmpShape, moveIsPossible, minDeltaX, maxDeltaX);

      moveIsPossible = result[0];
      minDeltaX = result[1];
      maxDeltaX = result[2];

      // Consider all allowed translations for the current rotation.
      if (0 != moveIsPossible) {
        for (trialTranslationDelta = minDeltaX; trialTranslationDelta <= maxDeltaX; trialTranslationDelta++) {
					// Evaluate this move

          // Copy piece to temp and rotate and translate
          tmpShape = new CurrentShape(s);

          for (int count = 0; count < trialRotationDelta; count++) {
            tmpShape.rotateLeft(g.getTGrid());
          }

          tmpShape.move(trialTranslationDelta, 0);

          moveAcceptable = g.IsGoalAcceptable(tmpShape);

          if (0 != moveAcceptable) {
            // Since the piece can be (not necessarily GET) at the goal
            // horizontal translation and orientation, it's worth trying
            // out a drop and evaluating the move.
            tmpGrid = new Grid(g);

            tmpGrid.FullDropAndAddPieceToBoard(tmpShape);

            trialPriority = 0;

            double weightRowElimination = (0.30);
            double weightTotalOccupiedCells = (-0.00);
            double weightTotalShadowedHoles = (-0.65);
            double weightPileHeightWeightedCells = (-0.10);
            double weightSumOfWellHeights = (-0.20);

            int rowsEliminated = tmpGrid.getNbFullLine();

            // Averages around 1310 rows in 10 games, with a min of 445 and a max of 3710.
            trialMerit = (weightRowElimination) * (double) (rowsEliminated);
            // trialMerit += (weightTotalOccupiedCells) * (double) (tmpGrid.TotalOccupiedCells());
            // trialMerit += (weightTotalShadowedHoles) * (double) (tmpGrid.TotalShadowedHoles());
            trialMerit += (weightPileHeightWeightedCells) * (double) (tmpGrid.PileHeightWeightedCells());
            trialMerit += (weightSumOfWellHeights) * (double) (tmpGrid.SumOfWellHeights());

            // If this move is better than any move considered before,
            // or if this move is equally ranked but has a higher priority,
            // then update this to be our best move.
            if ((trialMerit > currentBestMerit) || ((trialMerit == currentBestMerit) && (trialPriority > currentBestPriority))) {
              currentBestPriority = trialPriority;
              currentBestMerit = trialMerit;
              currentBestTranslationDelta = trialTranslationDelta;
              currentBestRotationDelta = trialRotationDelta;
            }
          }
        }
      }

    }

    // Commit to this move
    int[] returnValue = new int[3];
    returnValue[0] = currentBestTranslationDelta;
    returnValue[1] = currentBestRotationDelta;
    returnValue[2] = (int) currentBestMerit;

    return returnValue;
  }

  public void move() {
    CurrentShape s = getCurrentShape();
    int curX = s.getX(), curY = s.getY();
    if (bestRotationDelta != 0) {
      s.rotateLeft(boardGame.getGrid().getTGrid());
      --bestRotationDelta;
    } else if (bestTranslationDelta != curX) {
      if (curX > bestTranslationDelta) {
        left();
      } else {
        right();
      }
    } else {
      dropDown();
    }
  }

  @Override
  public void run() {
    while (!this.isFinish()) {
      if (!ContextManager.getInstance().isPaused && isTetris()) {
        int r = (int) (Math.random() * 100);
        if (r > 70 || GameEngine.getInstance().isPlayersInWordMode() || !canWorddle() ) {
          //Do movement
          int nbMove = (int) (Math.random() * 3);
          while (nbMove > 0 && isTetris()) {
            int[] result = gestBestMoveShape();
            bestTranslationDelta = result[0];
            bestRotationDelta = result[1];
            bestMerit = result[2];
            /*
             System.out.println("bestTranslation : " + bestTranslationDelta);
             System.out.println("bestRotation : " + bestRotationDelta);
             System.out.println("bestMerit : " + bestMerit);
             */
            move();
            nbMove--;
          }
          down(1);
          try {
            Thread.sleep(getSpeedFall());

          } catch (InterruptedException ex) {
            Logger.getLogger(RunPlayer.class
                    .getName()).log(Level.SEVERE, null, ex);
          }
        } else if( !GameEngine.getInstance().isPlayersInWordMode() && canWorddle() ) {
          switchToWorddle(true);
          boardGame.getGrid().setAllowClick(false);
          GameEngine.getInstance().beginWorddleTimer(this);
          stockCurrentShape();
          addNewChar(getGrid().clickedOneBrick());
        }
      } else if (isAnagram()) {
        doAnagram();
      } else if (isWorddle()) {
        doWorddle();
      }

      updateObservateur(null);
    }

    updateObservateur("Perdu");

    ContextManager.getInstance().stop();
  }

}
