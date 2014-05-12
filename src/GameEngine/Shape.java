package GameEngine;

import Pattern.Observable;
import Pattern.Observer;
import java.awt.Color;
import java.util.ArrayList;

/**
 * <b> Shape represents a shape/piece in the tetris game. </b>
 * <p>
 * It's a logicial part of the game. </p>
 * <p>
 * Shape is Observable by the Shape2D. </p>
 * <p>
 * Shape contains: </p>
 * <ul>
 * <li> ShapeEnum: The different name that a Shape can have. </li>
 * <li> sizeShape: The size in x and y axes of a Shape. </li>
 * <li> name: The name of the Shape. </li>
 * <li> color: The color of the Shape. </li>
 * <li> reprensentation: An Array to represents with 1 and 0 the shape's form.
 * </li>
 * <li> composition: A Brick Array of the Shape's composition. </li>
 * </ul>
 *
 */
public class Shape implements Observable {

  public enum ShapeEnum {

    NoShape, LShape, MirroredLShape, SShape, ZShape, LineShape, SquareShape, TShape
  };

  public final static int sizeShape = 4;
  protected final String name;
  protected final Color color;
  protected int[][] representation;
  protected Brick[][] composition;
  private ArrayList<Observer> listObserver = new ArrayList<>();

  /**
   * Complete constructor.
   *
   * @param name The name
   * @param color The color
   * @param representation The representation
   * @param composition The composition
   */
  public Shape(String name, Color color, int[][] representation, Brick[][] composition) {
    this.name = name;
    this.color = color;
    this.representation = representation;
    this.composition = composition;
  }

  /**
   * Constructor. Create the Color and the Composition.
   *
   * @param name The name
   * @param color The color with an Array of 3 int
   * @param representation The representation
   */
  public Shape(String name, int[] color, int[][] representation) {
    this.name = name;
    this.color = new Color(color[0], color[1], color[2]);
    this.representation = representation;
    setComposition(representation);
  }

  /**
   * Construction. Create the Composition.
   *
   * @param name The name
   * @param color The Color
   * @param representation The representation
   */
  public Shape(String name, Color color, int[][] representation) {
    this.name = name;
    this.color = color;
    this.representation = representation;
    setComposition(representation);
  }

  /**
   * Copy constructor with or without compsition copy.
   *
   * @param shape The Shape to copy
   * @param copyComposition To copy or not the composition
   */
  public Shape(Shape shape, boolean copyComposition) {
    this(shape.name, shape.color, shape.representation);
    if (copyComposition) {
      this.composition = shape.composition;
    }
  }

  /**
   * To create a Shape composition.
   *
   * @param representation The Shape's representation
   */
  private void setComposition(int[][] representation) {
    this.composition = new Brick[representation.length][representation[0].length];
    Color c = this.getColor();
    for (int i = 0; i < representation.length; ++i) {
      for (int j = 0; j < representation[i].length; ++j) {
        if (representation[i][j] > 0) { //suivant comment est implémenter la représentation
          composition[i][j] = new Brick(1, c); //rajouter une lettre au hasard
        }
      }
    }
    updateObserver(null);
  }

  /**
   * Get the Shape's composition.
   *
   * @return The Shape's composition
   */
  public Brick[][] getComposition() {
    return composition;
  }

  /**
   * Get the color.
   *
   * @return The color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Get the name.
   *
   * @return The name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the Shape's representation.
   * 
   * @return The Shape's representation 
   */
  public int[][] getRepresentation() {
    return representation;
  }

  /**
   * Get the Shape's height on a single column.
   * 
   * @param x The column
   * @return The Shape's height
   */
  public int getHeight(int x) {
    int height = 0;
    for (int i = 0; i < representation.length; ++i) {
      if (representation[i][x] > 0) {
        height++;
      }
    }
    return height;
  }

  /**
   * Get the Shape's width.
   * 
   * @return The Shape's width
   */
  public int getWidth() {
    int maxWidth = 0;
    for (int i = 0; i < representation.length; ++i) {
      for (int j = 0; j < representation.length; ++j) {
        if (representation[i][j] > 0 && maxWidth < j) {
          maxWidth = j;
        }
      }
    }
    return maxWidth + 1;
  }

  /**
   * @see Oberservable
   */
  @Override
  public void addObserver(Observer obs) {
    listObserver.add(obs);
  }

  /**
   * @see Oberservable
   */
  @Override
  public void updateObserver(Object args) {
    for (Observer obs : listObserver) {
      obs.update(this, args);
    }
  }

  /**
   * @see Oberservable
   */
  @Override
  public void delAllObserver() {
    listObserver = new ArrayList<>();
  }
}
