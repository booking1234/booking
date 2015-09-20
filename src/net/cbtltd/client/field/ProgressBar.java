/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.field.spinner.ResizableWidget;
import net.cbtltd.client.field.spinner.ResizableWidgetCollection;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgressBar.
 */
public class ProgressBar 
extends Widget 
implements ResizableWidget {

	public int lo = 50; 
	public int lm = 25; 
	public int hm = 15;
	private Element barElement;
	private Integer curProgress;
	private Integer maxProgress;
	private Integer minProgress;
	private boolean textVisible = true;
	private Element textElement;
	private TextFormatter textFormatter;

	/**
	 * The formatter used to format the text displayed in the progress bar.
	 */
	public abstract static class TextFormatter {
		/**
		 * Gets the text to display in the ProgressBar based on its current value.
		 * 
		 * Override this method to change the text displayed within the progress bar.
		 * 
		 * @param bar the progress bar.
		 * @param curProgress the current progress percentage.
		 * @return the text to display in the progress bar.
		 */
		protected abstract String getText(ProgressBar bar, Integer curProgress);
	}

	/** Instantiates a new progress bar. */
	public ProgressBar() {this(0, 100, null);}

	/**
	 * Instantiates a new progress bar.
	 *
	 * @param minProgress the minimum progress percentage.
	 * @param maxProgress the maximum progress percentage.
	 */
	public ProgressBar(Integer minProgress, Integer maxProgress) {this(minProgress, maxProgress, null);}

	/**
	 * Instantiates a new progress bar.
	 * 
	 * @param minProgress the minimum progress percentage.
	 * @param maxProgress the maximum progress percentage.
	 * @param textFormatter the formatter to format the progress bar text.
	 */
	public ProgressBar(Integer minProgress, Integer maxProgress, TextFormatter textFormatter) {
		this.minProgress = minProgress;
		this.maxProgress = maxProgress;
		setTextFormatter(textFormatter);

		// Create the outer shell
		setElement(DOM.createDiv());
		DOM.setStyleAttribute(getElement(), "position", "relative");
		setStyleName(AbstractField.CSS.cbtProgressBar());

		// Create the bar element
		barElement = DOM.createDiv();
		DOM.appendChild(getElement(), barElement);
		DOM.setStyleAttribute(barElement, "height", "100%");
		setBarStyleName(AbstractField.CSS.cbtProgressBarBar());

		// Create the text element
		textElement = DOM.createDiv();
		DOM.appendChild(getElement(), textElement);
		DOM.setStyleAttribute(textElement, "position", "absolute");
		DOM.setStyleAttribute(textElement, "top", "0px");
	}

	/**
	 * Gets the current percentage complete, relative to the minimum and maximum
	 * values. The percentage must always be between 0.0 and 100.0.
	 *
	 * @return the current percentage complete.
	 */
	public int getPercent() {
		if (maxProgress <= minProgress) {return 0;}
		else {return (curProgress - minProgress) * 100 / (maxProgress - minProgress);}
	}

	/**
	 * Get the current progress percentage.
	 *
	 * @return the current progress percentage.
	 */
	public Integer getProgress() {
		return curProgress;
	}

	/**
	 * Check if the text is visible.
	 *
	 * @return true if the text is visible.
	 */
	public boolean isTextVisible() {
		return textVisible;
	}

	/**
	 * Called when the dimensions of the parent element change.
	 * Subclasses should override this method as needed.
	 *
	 * @param width the new client width of the element
	 * @param height the new client height of the element
	 */
	public void onResize(int width, int height) {
		if (textVisible) {
			int textWidth = DOM.getElementPropertyInt(textElement, "offsetWidth");
			int left = (width / 2) - (textWidth / 2);
			DOM.setStyleAttribute(textElement, "left", left + "px");
		}
	}

	/** Redraws the progress bar when something changes the layout. */
	public void redraw() {
		if (isAttached()) {
			int width = DOM.getElementPropertyInt(getElement(), "clientWidth");
			int height = DOM.getElementPropertyInt(getElement(), "clientHeight");
			onResize(width, height);
		}
	}

	/**
	 * Sets the CSS style of the bar.
	 *
	 * @param style the new CSS style.
	 */
	public void setBarStyleName(String style) {
		DOM.setElementProperty(barElement, "className", style);
	}

	/**
	 * Sets the maximum progress. If the minimum progress is more than the current
	 * progress, the current progress is adjusted to be within the new range.
	 *
	 * @param maxProgress the maximum progress.
	 */
	public void setMaxProgress(Integer maxProgress) {
		this.maxProgress = maxProgress;
		curProgress = Math.min(curProgress, maxProgress);
		resetProgress();
	}

	/**
	 * Sets the minimum progress. If the minimum progress is more than the current
	 * progress, the current progress is adjusted to be within the new range.
	 *
	 * @param minProgress the minimum progress.
	 */
	public void setMinProgress(Integer minProgress) {
		this.minProgress = minProgress;
		curProgress = Math.max(curProgress, minProgress);
		resetProgress();
	}

	/**
	 * Sets the current progress.
	 *
	 * @param curProgress the current progress.
	 */
	public void setProgress(Integer curProgress) {

		this.curProgress = Math.max(minProgress, Math.min(maxProgress, curProgress));
		// Calculate percent complete
		int percent = getPercent();
		DOM.setStyleAttribute(barElement, "width", percent + "%");
		DOM.setElementProperty(textElement, "innerHTML", generateText(curProgress));
		updateTextStyle(percent);
		redraw();
	}

	/**
	 * Sets the text formatter.
	 *
	 * @param textFormatter the new text formatter.
	 */
	public void setTextFormatter(TextFormatter textFormatter) {
		this.textFormatter = textFormatter;
	}

	/**
	 * Sets if the text is visible over the bar.
	 *
	 * @param textVisible is true if the text is visible over the bar.
	 */
	public void setTextVisible(boolean textVisible) {
		this.textVisible = textVisible;
		if (this.textVisible) {
			DOM.setStyleAttribute(textElement, "display", "");
			redraw();
		} 
		else {DOM.setStyleAttribute(textElement, "display", "none");}
	}

	/**
	 * Generates the text to display in the progress bar. Override this
	 * function to change the default progress percent to a more informative
	 * message, such as the number of kilobytes downloaded.
	 *
	 * @param curProgress the current progress percentage.
	 * @return the text to display in the progress bar.
	 */
	protected String generateText(Integer curProgress) {
		if (textFormatter != null) {return textFormatter.getText(this, curProgress);} 
		else {return getPercent() + "%";}
	}

	/**
	 * Called immediately after a widget becomes attached to the browser's document
	 * to reset the position attribute of its parent element.
	 */
	@Override
	protected void onLoad() {
		DOM.setStyleAttribute(getElement(), "position", "relative");
		ResizableWidgetCollection.get().add(this);
		redraw();
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onUnload()
	 */
	@Override
	protected void onUnload() {
		ResizableWidgetCollection.get().remove(this);
	}

	/** Reset the progress text based on the current minimum and maximum range. */
	protected void resetProgress() {
		setProgress(getProgress());
	}

	/*
	 * Updates the text style according to the specified progress percentage.
	 * 
	 * @param percent the specified progress percentage.
	 */
	private void updateTextStyle(int percent) {
		if (percent < lo) {
			DOM.setElementProperty(barElement, "className", AbstractField.CSS.cbtProgressBarLo());
			DOM.setElementProperty(textElement, "className", AbstractField.CSS.cbtProgressTextLo());
		}
		else if (percent < lo + lm) {
			DOM.setElementProperty(barElement, "className", AbstractField.CSS.cbtProgressBarLm());
			DOM.setElementProperty(textElement, "className", AbstractField.CSS.cbtProgressTextLm());
		}
		else if (percent < lo + lm + hm) {
			DOM.setElementProperty(barElement, "className", AbstractField.CSS.cbtProgressBarHm());
			DOM.setElementProperty(textElement, "className", AbstractField.CSS.cbtProgressTextHm());
		}
		else {
			DOM.setElementProperty(barElement, "className", AbstractField.CSS.cbtProgressBarHi());
			DOM.setElementProperty(textElement, "className", AbstractField.CSS.cbtProgressTextHi());
		}
	}
}