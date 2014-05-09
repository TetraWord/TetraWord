package GameEngine;

import ContextManager.ContextManager;
import GameEngine.Dictionnary.Dictionary;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <b> AI is the Artificial Intelligence of the game. </b>
 * <p>
 * AI inherits from the Player. It's a Player who can play alone.
 * AI is a Runnable. 
 * </p>
 * <p>
 * The AI can do Tetris mode and Anagram mode. 
 * </p>
 */
public class AI extends Player implements Runnable {

  /**
   * The best translation that the CurrentShape in the Grid must do.
   * @see AI#getBestMoveShape() 
   */
  private int bestTranslation;
  /**
   * The best rotation that the CurrentShape in the Grid must do.
   * @see AI#getBestMoveShape() 
   */
  private int bestRotation;
  /**
   * The best score of the CurrentShape's position.
   * @see AI#getBestMoveShape() 
   */
  private int bestMerit;

  /**
   * AI constructor.
   * 
   * @see Player#Player(int, GameEngine.Shape, GameEngine.Shape, GameEngine.Dictionnary.Dictionary) 
   * @param nb The number of the Player
   * @param s The first Shape of the Player
   * @param s2 The second Shape of the Player
   * @param d The dictionnary
   */
  public AI(int nb, Shape s, Shape s2, Dictionary d) {
    super(nb, s, s2, d);
  }

  /**
   * Override the doAnagram method of the Player.
   * AI get back all the possible word possible in the Line to remove. 
   * AI choose one of this word and click all the Brick needed to do this word.
   */
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
        Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
      }
      int r = (int) (Math.random() * 100);
      System.out.println("r vaut : "+r);
      if (r < 25) {
        //Select the bestWord
        bestWord = bestWords[0];
      } else if (r > 25 && r < 75) {
        bestWord = bestWords[(int) (Math.random() * bestWords.length - 2)];
      } else {
        bestWord = bestWords[bestWords.length - 1];
      }
      for (int i = 0; i < bestWord.length(); ++i) {
        g.selectBrick(g.getFirstFullLine(), bestWord.charAt(i));
        addNewChar(bestWord.charAt(i));
        try {
          Thread.sleep(700);
        } catch (InterruptedException ex) {
          Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      try {
        Thread.sleep(1200);
      } catch (InterruptedException ex) {
        Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
      }
      verifAnagram(bestWord);
      clearWord();
      getGrid().removeLine(getGrid().getFirstFullLine());
      ++numLinesRemoved;
    }

    finishAnagram(numLinesRemoved);
  }

  /**
   * Override the doWorddle method of the Player.
   * Not implemented.
   */
  @Override
  public void doWorddle() {
    while (GameEngine.getInstance().timerWorddleIsAlive()) {

      String s = getWord();
      if (getDico().included(s)) {
        boardGame.getGrid().setBricksToDestroy();
        addToScore(s.length() * 3);
      } else {
        boardGame.getGrid().clearTabBrickClicked();
        addToScore(-s.length() * 4);
      }
      clearWord();
      boardGame.getGrid().setAllowDoubleClick(true);
      updateObserver(null);
    }
    finishWorddle();
  }

  /**
   * Get the best number of translation and rotation that the CurrentShape must do to go to the better place of the Grid.
   * @return The best translation, the best rotation and the coefficient of the best place.
   */
  public int[] getBestMoveShape() {

    CurrentShape s = getCurrentShape();
    Grid g = boardGame.getGrid();
    int width = Grid.sizeX;
    int height = Grid.sizeY;

    int currentBestTranslationDelta = 0;
    int currentBestRotationDelta = 0;
    double currentBestMerit = (-1.0e20);
    int currentBestPriority = 0;

    int trialTranslationDelta;
    int trialRotationDelta;
    double trialMerit;
    int trialPriority;

    int moveAcceptable;

    CurrentShape tmpShape;
    Grid tmpGrid;

    int maxOrientations = 4;

    for (trialRotationDelta = 0; trialRotationDelta < maxOrientations; ++trialRotationDelta) {
      // Make temporary copy of piece, and rotate the copy.
      tmpShape = new CurrentShape(s);

      for (int count = 0; count < trialRotationDelta; ++count) {
        tmpShape.rotateLeft(g.getTGrid());
      }

      // Determine the translation limits for this rotated piece.
      int moveIsPossible;
      int minDeltaX;
      int maxDeltaX;

      int[] result = g.DetermineAccessibleTranslationsForShapeOrientation(tmpShape);

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

            tmpGrid.FullDropAndAddShapeToGrid(tmpShape);

            trialPriority = 0;

            double weightRowElimination = (0.30);
            double weightTotalOccupiedCells = (-0.00);
            double weightTotalShadowedHoles = (-0.65);
            double weightPileHeightWeightedCells = (-0.10);
            double weightSumOfWellHeights = (-0.20);

            int rowsEliminated = tmpGrid.getNbFullLine();

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

  /**
   * Move the CurrentShape to the best position determines by the getBestMoveShape method.
   * @see AI#getBestMoveShape() 
   */
  public void move() {
    CurrentShape s = getCurrentShape();
    int curX = s.getX(), curY = s.getY();
    if (bestRotation != 0) {
      s.rotateLeft(boardGame.getGrid().getTGrid());
      --bestRotation;
    } else if (bestTranslation != curX) {
      if (curX > bestTranslation) {
        left();
      } else {
        right();
      }
    } else {
      dropDown();
    }
  }

  /**
   * To launch an AI.
   * AI choose the best place to the CurrentShape and try to move it to this place. 
   * When the Worddle mode is ready, the AI has a chance to go to this mode.
   * 
   */
  @Override
  public void run() {
    while (!this.isFinish()) {
      if (!ContextManager.getInstance().isPaused && isTetris()) {
        int r = (int) (Math.random() * 100);
        if (r > 20 || GameEngine.getInstance().isPlayersInWordMode() || !canWorddle()) {
          //Do movement
          int nbMove = (int) (Math.random() * 3);
          while (nbMove > 0 && isTetris()) {
            int[] result = getBestMoveShape();
            bestTranslation = result[0];
            bestRotation = result[1];
            bestMerit = result[2];
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
        } /*else if (!GameEngine.getInstance().isPlayersInWordMode() && canWorddle()) {
          switchToWorddle(true);
          boardGame.getGrid().setAllowClick(false);
          GameEngine.getInstance().beginWorddleTimer(this);
          stockCurrentShape();
          addNewChar(getGrid().clickedOneBrick());
        } */
      } else if (isAnagram()) {
        doAnagram();
      } else if (isWorddle()) {
        doWorddle();
      }

      updateObserver(null);
    }

    updateObserver("Perdu");

    ContextManager.getInstance().stop();
  }

}
