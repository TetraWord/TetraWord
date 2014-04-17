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

public class DesignMenu2D extends Menu2D {

  DesignMenu dm;
  String apercuBackground;
  String[] bricks;
  ButtonGroup group = new ButtonGroup();
  ComboBox combo;

  public DesignMenu2D() {
    super();
    state = MenuState.DESIGN;
    lastState = MenuState.OPTION;

    int sx = 150, sy = 50;
    int x = WINDOW_WIDTH / 2 - sx;
    int y = 50;
    int step_x = 200;

    dm = new DesignMenu();

    JLabel label = new JLabel("Choose a theme");
    label.setBounds(x, y, sx, sy);
    label.setFont(new Font("Champagne & Limousines", 20, 20));
    label.setForeground(Color.white);
    this.add(label);

    x = x + step_x;
    combo = new ComboBox("Choose theme", dm.getNames(), x, y, sx, sy);
    combo.setFont(new Font("Champagne & Limousines", 20, 20));
    this.add(combo);

    sx = 300;
    x = WINDOW_WIDTH / 2 - sx / 2;
    y = 600;
    int step_y = 100;
    setButton2D(2);
    Button2D b = new Button2D("Save design", x, y, sx, sy);
    b.removeListener();
    b.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        RadioButton2D brick = getSelectedButtonText(group);
        String imgBrick = brick.getImage();

        String font;
        String[] color;
        String colors;
        /*Open propertie file of selected design to read it*/
        String file = "conf/Design/" + combo.getSelectedItem() + ".properties";
        Properties readprop = new Properties();
        InputStream input = null;
        /*Get infos from file*/
        try {

          input = new FileInputStream(file);

          // load a properties file
          readprop.load(input);

          font = readprop.getProperty("font");
          color = readprop.getProperty("color").split(",");
          colors = color[0] + "," + color[1] + "," + color[2];
          Properties prop = new Properties();
          OutputStream output = null;
          try {

            output = new FileOutputStream("conf/design.properties");

            // set the properties value
            prop.setProperty("background", apercuBackground);
            prop.setProperty("brick", imgBrick);
            prop.setProperty("font", font);
            prop.setProperty("color", colors);

            // save properties to project root folder
            prop.store(output, null);
            JOptionPane.showMessageDialog(null, "Design saved");

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
    this.add(new Button2D("Previous", x, y, sx, sy));

    loadApercus((String) combo.getSelectedItem());
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawApercuBackground(g, apercuBackground);
    drawApercuBricks(g, bricks);
    this.validate();
  }

  private void drawApercuBackground(Graphics g, String file) {
    try {
      Image img = ImageIO.read(new File(apercuBackground));
      int width = WINDOW_WIDTH / 3;
      int height = WINDOW_HEIGHT / 3;
      g.drawImage(img, WINDOW_WIDTH / 2 - width / 2, 150, width, height, this);
    } catch (IOException ex) {
      Logger.getLogger(Menu2D.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void drawApercuBricks(Graphics g, String[] bricks) {
    int x = WINDOW_WIDTH / 4;
    int y = WINDOW_HEIGHT / 2 + 40;
    int step = WINDOW_WIDTH / 7;

    JLabel label = new JLabel("Choose your bricks");
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

  public void loadApercus(String item) {

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
