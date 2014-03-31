
package ContextManager;

import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ItemActionListener implements ItemListener{

	@Override
	public void itemStateChanged(ItemEvent e) {
		ContextManager c = ContextManager.getInstance();
		ItemSelectable itemSelector = e.getItemSelectable();
		Object[] combo = itemSelector.getSelectedObjects();
		
		if( combo != null ){
			String item = (String)combo[0];
			c.reloadApercuBackground(item);
		}
	}
}
