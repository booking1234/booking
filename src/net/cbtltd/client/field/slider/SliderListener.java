/**
 * @author	Bess Siegal, CBT Limited, London, UK
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.slider;

/** The Interface SliderListener must be implemented to handle slider events. */
public interface SliderListener
{
    /**
     * This event is triggered when the user starts sliding.
     * 
     * @param e SliderEvent
     */
    public void onStart(SliderEvent e);
    
    /**
     * This event is triggered on every mouse move during slide. 
     * Returns false in order to prevent a slide, based on a value.
     * 
     * @param e SliderEvent
     * @return boolean false to prevent the slide
     */
    public boolean onSlide(SliderEvent e);
    
    /**
     * This event is triggered on slide stop, or if the value is changed programmatically (by the value method). 
     * Use SliderEvent.hasOriginalEvent() to detect whether the value changed by mouse or keyboard.  When false
     * it means the change was done programmatically. 
     * 
     * @param e SliderEvent
     */
    public void onChange(SliderEvent e);
    
    /**
     * This event is triggered when the user stops sliding.
     * 
     * @param e SliderEvent
     */
    public void onStop(SliderEvent e);
}
