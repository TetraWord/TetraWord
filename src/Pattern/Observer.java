package Pattern;

/**
 * <b>A class can implement the Observer interface when it wants to be informed of changes in observable objects.</b>
 * 
 */
public interface Observer {

	/**
	 * This method is called whenever the observed object is changed.
	 * @param o The observable object
	 * @param args Arguments passed 
	 */
  public void update(Observable o, Object args);
}
