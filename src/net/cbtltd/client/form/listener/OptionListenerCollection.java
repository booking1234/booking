package net.cbtltd.client.form.listener;

import java.util.ArrayList;

public class OptionListenerCollection {
	private ArrayList<OptionListener> listeners = new ArrayList<OptionListener>();

	public void fireOptionChange(String option){
		for (OptionListener listener: listeners)
			listener.onOptionChange(option);
	}

	public void add(OptionListener listener){
		listeners.add(listener);
	}

	public void remove(OptionListener listener){
		listeners.remove(listener);
	}
}
