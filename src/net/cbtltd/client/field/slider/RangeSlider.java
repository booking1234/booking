/**
 * @author	Bess Siegal, CBT Limited, London, UK
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.slider;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;

/** The Class RangeSlider is a convenient sub class of Slider for ranges. */
public class RangeSlider extends Slider
{    
    /**
     * Instantiates new RangeSlider instances.
     * @param id the element ID
     * @param min the the minimum possible value of the slider
     * @param max the the maximum possible value of the slider
     * @param defaultMin the the default value of the lowest anchor
     * @param defaultMax the the default value of the highest anchor
     */
    public RangeSlider(String id, int min, int max, int defaultMin, int defaultMax)
    {
        super(id, getOptions(min, max, defaultMin, defaultMax));
    }

    /**
     * A convenient way to create an options JSONObject for the RangeSlider.
     * 
     * @param min the default minimum of the slider
     * @param max the default maximum of the slider
     * @param defaultMin the the default value of the lowest anchor
     * @param defaultMax the the default value of the highest anchor
     * @return a JSONObject of RangeSlider options
     */
    public static JSONObject getOptions(int min, int max, int defaultMin, int defaultMax) 
    {
        JSONObject options = Slider.getOptions(min, max, new int[]{defaultMin, defaultMax});
        options.put(SliderOption.RANGE.toString(), JSONBoolean.getInstance(true));        
        return options;
    }
    
    /**
     * Convenience method for when range is true, gets the minimum of the selected range, or in other words,
     * gets the value of the lower anchor.
     * 
     * @return the value
     */
    public int getValueMin()
    {
        return getValueAtIndex(0);
    }
    
    /**
     * Convenience method for when range is true, gets the maximum of the selected range, or in other words,
     * gets the value of the higher anchor.
     * 
     * @return the value
     */
    public int getValueMax()
    {
        return getValueAtIndex(1);
    }

    /**
     * Convenience method for when range is true, sets both the min and max anchors.
     * 
     * @param min the the lower anchor's value
     * @param max the the upper anchor's value
     */
    public void setValues(int min, int max)
    {
        setValues(new int[]{min, max});
    }
    
}