/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Currency;

/** The Class MoneyField is to display and change a money value and currency. */
public class MoneyField
extends DoubleunitField {
	
	/**
	 * Instantiates a new money field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public MoneyField(
			HasComponents form, 
			short[] permission, 
			String label,
			int tab) {
		super(form, permission, Currency.getConvertibleCurrencyNameIds(), label, AbstractField.AF, tab);
	}
}
