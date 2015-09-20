package net.cbtltd.trial.uibinder;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
//import com.ited.lang.exception.CodeRuntimeException;
//import com.ited.lang.exception.IllegalArgumentRuntimeException;
//import com.ited.lang.misc.UtilsCollection;

/**
 * A composite extension that will make sure that the encapsulated widget will be created when needed. It makes it easier and more clear to
 * encapsulate a widget and to reuse an existing composite by subclassing it. This is latter issue is hard when subclassing from the 
 * GWT Composite class as you have to create the encapsulated widgets in the constructor ((anti OO-pattern). As such that modifying, 
 * like wrapping it, the encapsulated widgets is almost impossible.<br>
 * 
 * It will lazily create the encapsulated widget when the element is accessed (see for example: overridden methods 
 * {@link #setStyleName(String)})<br>

 * It will create the widget when attached to his parent, when not needed before.
 * As such this class overcomes problems (bugs) when the element is accessed and isn't created (encapsulated) yet and there 
 * is not need to explicitly call a <code>create</code> method.
 * 
 * @author Ed Bras. Last edited by: $Author: ed $
 * @version $Revision: 12686 $ - - $Date: 2011-09-27 01:55:58 +0200 (di, 27 sep 2011) $
 * @see http://groups.google.com/group/google-web-toolkit/browse_thread/thread/3cc28ae54f8ba979
 */
public abstract class SimpleComposite extends Composite {

	private Boolean created = false;

	private String primaryStyle;

	private String styleName;

	private List<String> depStyles;

	private List<String> addStyles;

	private boolean creationInProgress;

	private boolean isOnCreatedCalled;

	private String debugId;

	/**
	 * We will store the style name when the widget isn't created yet, to set it latter when it's created (see below).<br>
	 * Note: you can't trigger a create when the widget isn't created yet, as the user might set (public) properties on the widget 
	 * after calling this  method, which might then be ignored, as these (public) properties might be used during creation 
	 * (this was the reason why we choose this construction)!
	 */
	@Override
	public void setStyleName(final String style) {
		if (isCreated()) {
			super.setStyleName(style);
		}
		else {
			this.styleName = style;
		}
	}

	@Override
	public void removeStyleName(final String style) {
		addOrRemoveStyleName(style, false);
	}

	@Override
	public void addStyleName(final String style) {
		addOrRemoveStyleName(style, true);
	}

	/**
	 * See the comment of {@link #setStyleName(String)}
	 */
	@Override
	public void setStylePrimaryName(final String style) {
		if (isCreated()) {
			super.setStylePrimaryName(style);
		}
		else {
			this.primaryStyle = style;
		}
	}

	@Override
	public void addStyleDependentName(final String style) {
		addOrRemoveDependentStyleName(style, true);
	}

	@Override
	public void removeStyleDependentName(final String style) {
		addOrRemoveDependentStyleName(style, false);
	}

	@Override
	public Element getElement() {
		createWidget();
		return super.getElement();
	}

	/**
	 * Indicate if this instance is created.
	 */
	public boolean isCreated() {
		return this.created;
	}

	/**
	 * Important override to overcome strange behavior during debugging.<br>
	 * Example: during a breakpoint pause, during the widget creation, the toString can be triggered when watching 
	 * variables, such that another unwanted creation is triggered behind the scenes, that can causes unpredictable values 
	 * of properties.  
	 */
	@Override
	public String toString() {
		return isCreated() ? super.toString() : getClass().getName() + "\nNot created yet";

	}

	/**
	 * Not allowed to be overridden
	 */
	@Override
	protected final void initWidget(final Widget widget) {
		super.initWidget(widget);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		if (!this.isOnCreatedCalled) {
			this.isOnCreatedCalled = true;
			onCreatedCalled();
		}
	}

	/**
	 * First create the widget that will be encapsulated before the super <code>onAttach()</code> is called.
	 */
	@Override
	protected void onAttach() {
		// create widget that we encapsulate
		createWidget();

		super.onAttach();
	}

	/**
	 * Will automatically forward the debug id to contained widget.
	 */
	@Override
	protected void onEnsureDebugId(final String id) {
		super.onEnsureDebugId(id);
		if (getWidget() == null) {
			this.debugId = id;
		}
		else {
			setWidgetDebugId(id);
		}
	}

	/**
	 * Adapter method.<br>
	 * Is called after {@link #create()} is called and the created widget is attached to the body. <br>
	 * It's only called once during the widget live cycle (so not after every attach action).<br>
	 * Is useful to perform those widget-creation actions that need the widget to be attached.
	 */
	protected void onCreatedCalled() {
	}

	/**
	 * Implement this to create the widget that will be encapsulated.<br>
	 * Don't access the element of this instance in this method!! As such don't call methods like {@link #setStyleName(String)} 
	 * (this will cause an exception) Don't call this method directly!<br>
	 * {@link #isCreated()} will return true after this method is called, and not during this method call.<br>
	 * 
	 * @return encapsulated widget.
	 */
	protected abstract IsWidget create();

	private IsWidget createIntern() {
		if (this.creationInProgress) {
			throw new RuntimeException(getClass().getName() + " Creation in progress.\nThis=" + this);
		}
		this.creationInProgress = true;
		return create();
	}

	private void createWidget() {
		if (isCreated()) { // only called once
			return;
		}
		else {
			initWidget(createIntern().asWidget());
			this.created = true;
			this.creationInProgress = false;
		}

		addBuffered();
	}

	private void addBuffered() {
		// set styles
		if (this.styleName != null) {
			super.setStyleName(this.styleName);
			this.styleName = null;
		}
		if (this.primaryStyle != null) {
			super.setStylePrimaryName(this.primaryStyle);
			this.primaryStyle = null;
		}
//		if (UtilsCollection.hasContent(this.depStyles)) {
//			for (final String dep : this.depStyles) {
//				super.addStyleDependentName(dep);
//			}
//			this.depStyles = null;
//		}
//		if (UtilsCollection.hasContent(this.addStyles)) {
//			for (final String add : this.addStyles) {
//				super.addStyleName(add);
//			}
//			this.addStyles = null;
//		}

		if (this.debugId != null) {
			setWidgetDebugId(this.debugId);
		}
	}

	private void setWidgetDebugId(final String id) {
		getWidget().ensureDebugId(id);
	}

	/**
	 * Buffer add/remove style operations.
	 */
	private void addOrRemoveStyleName(final String style, final boolean add) {
		if (style == null) {
			throw new RuntimeException(getClass().getName() + "Style is null.");
		}

		if (isCreated()) {
			if (add) {
				super.addStyleName(style);
			}
			else {
				super.removeStyleName(style);
			}
		}
		else {
			if (add) {
				if (this.addStyles == null) {
					this.addStyles = new ArrayList<String>();
				}
				this.addStyles.add(style);
			}
			else if (this.addStyles != null) {
				this.addStyles.remove(style);
			}
		}
	}

	/**
	 * Buffer add/remove dependent style operations.
	 */
	private void addOrRemoveDependentStyleName(final String style, final boolean add) {
		if (isCreated()) {
			if (add) {
				super.addStyleDependentName(style);
			}
			else {
				super.removeStyleDependentName(style);
			}
		}
		else {
			if (add) {
				if (this.depStyles == null) {
					this.depStyles = new ArrayList<String>();
				}
				this.depStyles.add(style);
			}
			else if (this.depStyles != null) {
				this.depStyles.remove(style);
			}
		}
	}

}
