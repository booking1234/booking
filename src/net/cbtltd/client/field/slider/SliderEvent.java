/**
 * @author	Bess Siegal, CBT Limited, London, UK
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.slider;

import com.google.gwt.user.client.Event;

/** The Class SliderEvent holds event values for the Slider. */
public class SliderEvent {
    private int[] m_values;
    private boolean m_hasOriginalEvent = true;
    private Slider m_source;
    private Event m_event;
    
    /**
     * Create a new slider event.
     * 
     * @param event the event received by JSNI called
     * @param source the Slider that fires the event
     * @param values the int array of values
     */
    public SliderEvent(Event event, Slider source, int[] values) {
        this(event, source, values, true);
    }

    /**
     * Create a new slider event.
     * 
     * @param event the event received by JSNI called
     * @param source the Slider that fires the event
     * @param values the integer array of values
     * @param hasOriginalEvent the boolean if the change came from a non-programmatic change such as mouse or keyboard event
     */
    public SliderEvent(Event event, Slider source, int[] values, boolean hasOriginalEvent) {
        m_source = source;
        m_event = event;
        m_values = values;
        m_hasOriginalEvent = hasOriginalEvent;
    }
    
    /**
     * @return Returns the JSNI returned JavaScriptObject event.
     */
    public Event getEvent() {
        return m_event;
    }

    /**
     * Get the source of the event.
     * 
     * @return Returns the source.
     */
    public Slider getSource() {
        return m_source;
    }
    
    /**
     * Get the values from the event.
     * 
     * @return Returns the value.
     */
    public int[] getValues() {
        return m_values;
    }

    /**
     * Does this event have an original event.
     * 
     * @return Returns the hasOriginalEvent.
     */
    public boolean hasOriginalEvent() {
        return m_hasOriginalEvent;
    }

}