package GraphicEngine;

import javax.swing.JRadioButton;

public class RadioButton2D extends JRadioButton {

  private final String name;
  private final String img;

  public RadioButton2D(String name, String img) {
    super(name);
    this.name = name;
    this.img = img;
  }

  @Override
  public String getName() {
    return name;
  }

  public String getImage() {
    return img;
  }
}
