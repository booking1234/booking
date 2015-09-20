/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

/**
 * The Class CaptchaField is to display distorted versions of text to verify that the user is a human.
 * This is used to prevent automated agents from registering on the system,
 */
public class CaptchaField extends Composite {
	private Grid grid = new Grid(1, 1);
	private static final String DIV_NAME = "recaptcha_div";

	/**
	 * Instantiates a new captcha field.
	 */
	public CaptchaField() {

		this.initWidget(grid);

		Label dummyLable = new Label();
		grid.setWidget(0, 0, dummyLable);
		Element divElement = dummyLable.getElement();
		divElement.setAttribute("id", DIV_NAME);
	}

	private void createChallenge() {createNewChallengeJSNI();}

	/**
	 * Gets the challenge field.
	 *
	 * @return the challenge field
	 */
	public String getChallengeField() {return getChallengeJSNI();}
	
	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getResponse() {return getResponseJSNI();}


	private native String getResponseJSNI()/*-{return $wnd.Recaptcha.get_response();}-*/;

	private native String getChallengeJSNI()/*-{return $wnd.Recaptcha.get_challenge();}-*/;

	private native void createNewChallengeJSNI()
	/*-{$wnd.Recaptcha.create("6LeLSsYSAAAAAD6gWx5K0JwsW-hg7cgvO3g0kNIy", "recaptcha_div", 
		{
			theme: "red", 
			callback: $wnd.Recaptcha.focus_response_field
		}
	);}-*/;

	private native void reloadChallengeJSNI()/*-{$wnd.Recaptcha.reload();}-*/;

	private native void destroyJSNI()/*-{$wnd.Recaptcha.destroy();}-*/;

	/**
	 * Creates the new challenge.
	 */
	public void createNewChallenge() {reloadChallengeJSNI();}

	/**
	 * Destroy captcha.
	 */
	public void destroyCaptcha() {destroyJSNI();}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Composite#onAttach()
	 */
	@Override
	protected void onAttach() {
		super.onAttach();
		createChallenge();
	}
}