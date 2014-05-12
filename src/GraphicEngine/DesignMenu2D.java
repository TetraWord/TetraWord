package GraphicEngine;

import GameEngine.MenuState;
import GameEngine.DesignMenu;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import static GraphicEngine.GraphicEngine.WINDOW_HEIGHT;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * <b> DesignMenu2D is the menu permmiting to choose theme preferences. </b>
 * <p>
 * DesignMenu2D inherits from Menu2D. </p>
 * <p>
 * DesignMenu2d contains:
 * <ul>
 * <li> A DesignMenu instance. </li>
 * <li> The pathname of the background image file of the actual chosen theme.
 * </li>
 * <li> The list of pathnames corresponding to bricks image files of the actual
 * chosen theme. </li>
 * <li> A ButtonGroup permiting to stock radio buttons to choose bricks. </li>
 * <li> A ComboBox permiting to make a drop-down list of themes. </li>
 * </ul>
 *
 * </p>
 *
 * @see Menu2D
 * @see DesignMenu
 */
public class DesignMenu2D extends Menu2D {

  /**
   * The model of DesignMenu.
   *
   * @see DesignMenu
   */
  private final DesignMenu dm;

  /**
   * The pathname of the background image file of the actual chosen theme.
   *
   */
  private String apercuBackground;

  /**
   * The list of pathnames corresponding to bricks image files of the actual
   * chosen theme.
   *
   */
  private String[] bricks;

  /**
   * Stock radio buttons to choose bricks.
   *
   */
  private final ButtonGroup group = new ButtonGroup();

  /**
   * Drop-down list of theme.
   *
   */
  private final ComboBox combo;

  /**
   * DesignMenu2D constructor.
   *
   * Initialize attributes of the class by creating Swing elements.
   *
   * @see DesignMenu2D#loadOverviews(String)
   * @see Menu2D#loadBackground()
   */
  public DesignMenu2D() {

    /* Call to the constructor of Menu2D */
    super();

    /* Update MenuState */
    state = MenuState.DESIGN;
    lastState = MenuState.OPTION;

    /* Useful variable */
    int sx = 150, sy = 50;
    int x = WINDOW_WIDTH / 2 - sx;
    int y = 50;
    int step_x = 200;

    /* Creation of DesignMenu model */
    dm = new DesignMenu();

    /* Label "choose theme" */
    JLabel label = new JLabel("Choisir un thème");
    label.setBounds(x, y, sx, sy);
    label.setFont(new Font("Champagne & Limousines", 20, 20));
    label.setForeground(Color.white);
    this.add(label);

    /* Drop-down list of themes */
    x = x + step_x;
    combo = new ComboBox("Choose theme", dm.getNames(), x, y, sx, sy);
    combo.setFont(new Font("Champagne & Limousines", 20, 20));
    this.add(combo);

    sx = 300;
    x = WINDOW_WIDTH / 2 - sx / 2;
    y = 600;
    int step_y = 100;
    setButton2D(2);

    /* Save button */
    Button2D b = new Button2D("Sauvegarder", x, y, sx, sy);
    b.removeListener();
    b.addActionListener(new ActionListener() {

      /* Listener of save button  */
      @Override
      public void actionPerformed(ActionEvent e) {
        RadioButton2D brick = getSelectedButtonText(group);
        String imgBrick = brick.getImage();

        String font;
        String[] color;
        String colors;
        String imgModifier;
        /* Open propertie file of selected design to read it */
        String file = "conf/Design/" + combo.getSelectedItem() + ".properties";
        Properties readprop = new Properties();
        InputStream input = null;
        /* Get infos from file */
        try {

          input = new FileInputStream(file);

          /* Load a properties file */
          readprop.load(input);

          font = readprop.getProperty("font");
          color = readprop.getProperty("color").split(",");
          colors = color[0] + "," + color[1] + "," + color[2];
          imgModifier = readprop.getProperty("modifier");
          Properties prop = new Properties();
          OutputStream output = null;
          try {

            output = new FileOutputStream("conf/design.properties");

            /* Set the properties value */
            prop.setProperty("background", apercuBackground);
            prop.setProperty("brick", imgBrick);
            prop.setProperty("modifier", imgModifier);
            prop.setProperty("font", font);
            prop.setProperty("color", colors);

            /* Save properties to project root folder*/
            prop.store(output, null);
            JOptionPane.showMessageDialog(null, "Préférences sauvegardées");

          } catch (IOException io) {
            io.printStackTrace();
          } finally {
            if (output != null) {
              try {
                output.close();
              } catch (IOException eio) {
                eio.printStackTrace();
              }
            }

          }
        } catch (IOException ex) {
          ex.printStackTrace();
        } finally {
          if (input != null) {
            try {
              input.close();
            } catch (IOException ioe) {
              ioe.printStackTrace();
            }
          }
        }

      }

      /* Get the selected button in the list of radio button for bricks image file */
      public RadioButton2D getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();

          if (button.isSelected()) {
            return (RadioButton2D) button;
          }
        }

        return null;
      }
    });

    this.add(b);
    y = y + step_y;
    this.add(new Button2D("Précédent", x, y, sx, sy));

    loadOverviews((String) combo.getSelectedItem());

    loadBackground();
  }

  /**
   * Override the function paintComponent of Swing elements.
   *
   * Draw oversiews of the background and the bricks depending on chosen theme.
   *
   * @param g Graphics to draw on
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawOverviewBackground(g);
    drawOverviewsBricks(g, bricks);
    this.validate();
  }

  /**
   * Draw oversiew of the background of the chosen theme.
   *
   * @param g Graphics to draw on
   */
  private void drawOverviewBackground(Graphics g) {
    try {
      Image img = ImageIO.read(new File(apercuBackground));
      int width = WINDOW_WIDTH / 3;
      int height = WINDOW_HEIGHT / 3;
      g.drawImage(img, WINDOW_WIDTH / 2 - width / 2, 150, width, height, this);
    } catch (IOException ex) {
      Logger.getLogger(Menu2D.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Draw oversiew of the bricks of the chosen theme.
   *
   * @param g Graphics to draw on
   * @param bricks Pathnames of bricks image files to draw
   */
  private void drawOverviewsBricks(Graphics g, String[] bricks) {
    int x = WINDOW_WIDTH / 4;
    int y = WINDOW_HEIGHT / 2 + 40;
    int step = WINDOW_WIDTH / 7;

    JLabel label = new JLabel("Choisir sa brique");
    this.add(label);

    for (int i = 0; i < bricks.length; ++i) {
      try {
        int width = 35;
        int height = 35;

        Image img = ImageIO.read(new File(bricks[i]));
        g.drawImage(img, x + i * step, y, width, height, this);

      } catch (IOException ex) {
        Logger.getLogger(Menu2D.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Update background image pathname and bricks image pathname if needed.
   *
   * @param item Name of the actual chosen theme
   */
  public void loadOverviews(String item) {

    int x = WINDOW_WIDTH / 4;
    int y = WINDOW_HEIGHT / 2 + 100;
    int step = WINDOW_WIDTH / 7;
    bricks = null;

    /*Get chosen theme*/
    String[] themes = dm.getNames();
    for (int i = 0; i < themes.length; ++i) {
      /*Change theme if needed*/
      if (themes[i].equals(item)) {
        apercuBackground = dm.getBackground(i);
        bricks = dm.getBricks(i);
      }
    }

    while (group.getElements().hasMoreElements()) {
      /*Empty button from group and Jpanel */
      AbstractButton b = group.getElements().nextElement();
      this.remove(b);
      group.remove(b);
    }
    /*Fill button to group and Jpanel with new bricks*/
    for (int i = 0; i < bricks.length; ++i) {
      RadioButton2D radio = new RadioButton2D("Brick", bricks[i]);
      radio.setOpaque(false);
      radio.setText("");
      radio.setBounds(x + 8 + i * step, y - 15, 20, 20);
      if (i == 0) {
        radio.setSelected(true);
      }

      group.add(radio);
      this.add(radio);
    }
  }
}
