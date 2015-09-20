package net.cbtltd.client.form.listener;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.client.HasCommand;
import net.cbtltd.client.form.Transition;

public class StateListenerCollection {
	private ArrayList<StateListener> listeners = new ArrayList<StateListener>();

	public void fireStateChange(ArrayList<Transition> fsm, String state){
		List<HasCommand> commands = new ArrayList<HasCommand>();
		for(Transition t : fsm){
//			Window.alert("FSM " + t.toString());
			if (t.getFromState().equalsIgnoreCase(state)) {commands.add(t.getCommand());}
		}
		for (StateListener listener: listeners)
			listener.onStateChange(commands);
	}

	public void add(StateListener listener){
		listeners.add(listener);
	}

	public void remove(StateListener listener){
		listeners.remove(listener);
	}
}
