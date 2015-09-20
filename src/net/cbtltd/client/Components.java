/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import java.util.ArrayList;

import net.cbtltd.client.form.Transition;

	/** The Class Components manages the components of a HasComponents type. */
	public class Components {
		private ArrayList<Component> components = new ArrayList<Component>();

		/**
		 * Checks if any of the components have changed since the last occurrence of setChanged().
		 *
		 * @return true, if any of the components have changed.
		 */
		public boolean hasChanged() {
			for (Component component : components) {
				if (component.hasChanged()) {return true;} //Window.alert("Components hasChanged" + component.getClass());
			}
			return false;
		}

		/** Resets the field used to determine if a component has changed on all the components. */
		public void setChanged(){
			for (Component component : components) {component.setChanged();}
		}

		/** Refresh all the components. */
		public void onRefresh(){
			for (Component component : components) {component.onRefresh();}
		}

		/** Reset all the components. */
		public void onReset(){
			for (Component component : components) {component.onReset();}
		}

		/**
		 * Invoke the option change handler of all the components.
		 *
		 * @param option the option that has changed.
		 */
		public void onOptionChange(String option){
			for (Component component : components) {component.onOptionChange(option);}
		}

		/**
		 * Handles changes to the state of the container.
		 * The finite state machine determines which commands are active in the new state.
		 * A list of these commands is passed to each of its component(s). 
		 *
		 * @param fsm the finite state machine of the container.
		 * @param state the new state of the container.
		 */
		public void onStateChange(ArrayList<Transition> fsm, String state) {
			if (fsm == null || state == null || state.isEmpty()) {return;}
			ArrayList<HasCommand> commands = new ArrayList<HasCommand>();
			for(Transition t : fsm){
				if (t != null && t.getFromState() != null && t.getFromState().equalsIgnoreCase(state)) {commands.add(t.getCommand());}
			}
			for (Component component : components) {component.onStateChange(commands);}
		}
		
		/**
		 * Sets if the fields are enabled and can be changed.
		 *
		 * @param enabled is true if the field can be changed.
		 */
		public void setEnabled(boolean enabled){
			for (Component component : components) {component.setEnabled(enabled);}
		}

		/**
		 * Adds the specified component to the container.
		 *
		 * @param component the component to be added.
		 */
		public void add(Component component) {components.add(component);}

		/**
		 * Removes the specified component from the container.
		 *
		 * @param component the component to be removed.
		 */
		public void remove(Component component) {components.remove(component);}
	}

