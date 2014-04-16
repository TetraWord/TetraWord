
package GraphicEngine;

import GameEngine.MenuState;
import static GraphicEngine.GraphicEngine.WINDOW_WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class DifficultyMenu2D extends Menu2D {

	JCheckBox shadow;
	
	public DifficultyMenu2D() {
		defineMenu();
	}
	
	

	void defineMenu() {
		state = MenuState.DIFFICULTY;
    lastState = MenuState.OPTION;
		
		int sx = 300, sy = 50;
    int x = WINDOW_WIDTH / 2 - sx / 2;
    int y = 50;
    int step_y = 50 + sy;
		
		/* Title */
		JLabel title = new JLabel("Difficulty");
    title.setBounds(x, y, sx, sy);
    title.setFont(new Font("Champagne & Limousines", 50, 50));
    title.setForeground(Color.white);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
    this.add(title);
		
		y = y + step_y;
		
		/* Shadow */
		JLabel label = new JLabel("Show shadow");
    label.setBounds(x, y, sx, sy);
    label.setFont(new Font("Champagne & Limousines", 20, 20));
    label.setForeground(Color.white);
    this.add(label);
		
		x = x + sx / 2;
		shadow = new JCheckBox("");
    shadow.setBounds(x, y, sx, sy);
		shadow.setOpaque(false);
		shadow.putClientProperty("JComponent.sizeVariant", "large");
    this.add(shadow);
		
		y = y + step_y;
		
		x = x - sx / 2;
		
    setButton2D(2);
    Button2D b = new Button2D("Save options", x, y, sx, sy);
    b.removeListener();
    b.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
				boolean shadowBool = shadow.isSelected();

        Properties prop = new Properties();
        OutputStream output = null;
        try {

          output = new FileOutputStream("conf/options.properties");

          // set the properties value
          prop.setProperty("shadow", Boolean.toString(shadowBool));

          // save properties to project root folder
          prop.store(output, null);
          JOptionPane.showMessageDialog(null, "Options saved");

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
      }
    });

    this.add(b);
    y = y + step_y;
    this.add(new Button2D("Previous", x, y, sx, sy));
    
	}

	
}
