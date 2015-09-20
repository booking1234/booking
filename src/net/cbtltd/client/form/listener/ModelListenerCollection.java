package net.cbtltd.client.form.listener;

import java.util.ArrayList;

public class ModelListenerCollection {
	private ArrayList<ModelListener> listeners = new ArrayList<ModelListener>();

	public void refreshModel(){
		for (ModelListener listener : listeners)
			listener.onModelRefresh();
	}

	public void resetModel(){
		for (ModelListener listener : listeners)
			listener.onModelReset();
	}

	public void add(ModelListener listener){
		listeners.add(listener);
	}

	public void remove(ModelListener listener){
		listeners.remove(listener);
	}
}

