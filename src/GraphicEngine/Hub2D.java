
package GraphicEngine;

import GameEngine.Hub;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;

public class Hub2D extends JPanel{
	
	private final Hub hub;
	
	public Hub2D( Hub hub ){
		
    this.setLayout(null);
    this.setSize(650, 889);
    this.setOpaque(false);	
    this.setVisible(true);
		
		this.hub = hub;
		
		/* UI Level */
    JLabel level = new JLabel(Integer.toString(hub.getLevel()), CENTER);
    level.setBounds(0, 0, 50, 50);
    level.setFont(new Font("Arial", Font.BOLD, 22));
    this.add(level);
	}
	
	
	/*private void setJLabel(JLabel jl, int x, int y, int sx, int sy) {
    jl.setBounds(x, y, sx, sy);
		jl.setFont(new Font("Arial", Font.BOLD, 22));

  }*/

  /*public void setLevelUp(String level) {
    this.level.setText(level);
  }*/
	
	
	
	
	
}
