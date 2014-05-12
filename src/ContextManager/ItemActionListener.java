package ContextManager;

import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * <b>ItemActionListener is a listener for drop-down list.</b>
 * <p>
 * Specialized for needs of the drop-down list of the DesignMenu2D.
 * </p>
 * 
 * @see ItemListener
 */
public class ItemActionListener implements ItemListener {

	/**
	 * Reload overviews if needed.
	 * @param e ItemEvent
	 */
  @Override
  public void itemStateChanged(ItemEvent e) {
    ContextManager c = ContextManager.getInstance();
    ItemSelectable itemSelector = e.getItemSelectable();
    Object[] combo = itemSelector.getSelectedObjects();

    if (combo != null) {
      String item = (String) combo[0];
      c.reloadOverviewBackground(item);
    }
  }
}
