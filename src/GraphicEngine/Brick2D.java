
package GraphicEngine;

import GameEngine.Brick;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Brick2D {
	private final Brick model;
	
	public Brick2D(Brick model){
		this.model = model;
	}
	
	public void draw(Graphics g, int top, int left, BufferedImage img){
		g.drawImage(img, top, left, null);
		String letter = Character.toString( model.getLetter() );
		String toUpperCase = letter.toUpperCase();
		Font font = new Font("Arial",Font.BOLD,20);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString( toUpperCase, top + 10, left + 25);
	}
	
	
	
	
}
