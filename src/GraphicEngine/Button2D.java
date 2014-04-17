package GraphicEngine;

import ContextManager.ContextManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Button2D extends JButton {

  private final String name;

  public Button2D(String name, int x, int y, int sx, int sy) {
    super(name);
    this.name = name;
    this.setBounds(x, y, sx, sy);
    this.setFocusable(false);
    this.addActionListener(ContextManager.getMenuListener());

    /*Design*/
    this.setIcon(new ImageIcon("media/Design/btnBG.jpg"));

    this.setVerticalTextPosition(SwingConstants.CENTER);
    this.setHorizontalTextPosition(SwingConstants.CENTER);
    this.setForeground(Color.white);
    this.setFont(new Font("Champagne & Limousines", 30, 30));
  }

  public void removeListener() {
    this.removeActionListener(ContextManager.getMenuListener());
  }

  @Override
  public String getName() {
    return name;
  }
}
