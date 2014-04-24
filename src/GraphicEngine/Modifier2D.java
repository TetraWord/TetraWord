package GraphicEngine;

import GameEngine.Modifier;

public class Modifier2D {
	private final Modifier model;
	private static String modifierImg;
	
	public Modifier2D(Modifier model) {
		this.model = model;
	}
	
	public static void setModifierImage(String modifierImg) {
		Modifier2D.modifierImg = modifierImg;
	}

}