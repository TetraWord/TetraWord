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
import java.util.Enumeration;
import java.util.Properties;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

class DifficultyMenu2D extends Menu2D {

  private JCheckBox shadow;
	private ButtonGroup speedFall = new ButtonGroup();

  public DifficultyMenu2D() {
    defineMenu();
  }

  private void defineMenu() {
    state = MenuState.DIFFICULTY;
    lastState = MenuState.OPTION;

    int sx = 300, sy = 50;
    int x = WINDOW_WIDTH / 2 - sx / 2;
    int y = 50;
    int step_y = 50 + sy;

    /* Title */
    JLabel title = new JLabel("Difficulté");
    title.setBounds(x, y, sx, sy);
    title.setFont(new Font("Champagne & Limousines", 50, 50));
    title.setForeground(Color.white);
    title.setHorizontalAlignment(JLabel.CENTER);
    title.setVerticalAlignment(JLabel.CENTER);
    this.add(title);

    y = y + step_y;

    /* Shadow */
    JLabel label = new JLabel("Montrer l'ombre");
    label.setBounds(x, y, sx, sy);
    label.setFont(new Font("Champagne & Limousines", 20, 20));
    label.setForeground(Color.white);
    this.add(label);

    x = x + sx / 2;
    y = y + 15;
    shadow = new JCheckBox("");
    shadow.setBounds(x, y, 20, 20);
    shadow.setOpaque(false);
    shadow.putClientProperty("JComponent.sizeVariant", "large");
    this.add(shadow);

    y = y + step_y/2 - 15;

    x = x - sx / 2;
		
		/* Speed fall */
		JLabel speed = new JLabel("Vitesse de chute");
    speed.setBounds(x, y, sx, sy);
    speed.setFont(new Font("Champagne & Limousines", 20, 20));
    speed.setForeground(Color.white);
    this.add(speed);
			
    x = x + sx / 2;
    y = y + 15;
			JRadioButton lent = new JRadioButton("Lent");
      lent.setOpaque(false);
      lent.setText("Lent");
      lent.setName("Lent");
      lent.setBounds(x, y, 80, 20);
      lent.setSelected(false);

      speedFall.add(lent);
      this.add(lent);
			
    x = x + 80;
			JRadioButton moyen = new JRadioButton("Moyen");
      moyen.setOpaque(false);
      moyen.setText("Moyen");
      lent.setName("Moyen");
      moyen.setBounds(x, y, 80, 20);
      moyen.setSelected(true);

      speedFall.add(moyen);
      this.add(moyen);
			
    x = x + 80;
			JRadioButton rapide = new JRadioButton("Rapide");
      rapide.setOpaque(false);
      rapide.setText("Rapide");
      lent.setName("Rapide");
      rapide.setBounds(x, y, 80, 20);
      rapide.setSelected(false);

      speedFall.add(rapide);
      this.add(rapide);
			
		
    x = WINDOW_WIDTH / 2 - sx / 2;
    y = y + step_y;
		
    setButton2D(2);
    Button2D b = new Button2D("Sauvegarder", x, y, sx, sy);
    b.removeListener();
    b.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        boolean shadowBool = shadow.isSelected();
				JRadioButton speedSelected = getSelectedButtonText(speedFall);
				int speed;
				switch(speedSelected.getText()){
					case "Lent":
						speed = 1250;
						break;
					case "Moyen":
						speed = 1000;
						break;
					case "Rapide":
						speed = 750;
						break;
					default: 
						speed = 1000;
						break;
				}

        Properties prop = new Properties();
        OutputStream output = null;
        try {

          output = new FileOutputStream("conf/options.properties");

          // set the properties value
          prop.setProperty("shadow", Boolean.toString(shadowBool));
          prop.setProperty("speed", Integer.toString(speed));

          // save properties to project root folder
          prop.store(output, null);
          JOptionPane.showMessageDialog(null, "Options sauvegardées");

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
			
			private JRadioButton getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
          AbstractButton button = buttons.nextElement();

          if (button.isSelected()) {
            return (JRadioButton) button;
          }
        }

        return null;
				}
    });

    this.add(b);
    y = y + step_y;
    this.add(new Button2D("Précédent", x, y, sx, sy));
		
		loadBackground();

  }

}
