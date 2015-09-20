package net.cbtltd.client.form.listener;

import java.util.ArrayList;

public class EnableListenerCollection {
	private ArrayList<EnableListener> listeners = new ArrayList<EnableListener>();

	public void fireEnableChange(String key){
		for (EnableListener listener: listeners)
			listener.onEnableChange(key);
	}

	public void add(EnableListener listener){
		listeners.add(listener);
	}

	public void remove(EnableListener listener){
		listeners.remove(listener);
	}
}
