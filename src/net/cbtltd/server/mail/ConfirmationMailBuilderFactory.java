package net.cbtltd.server.mail;

public class ConfirmationMailBuilderFactory {
	
	private static ConfirmationMailBuilderFactory factory = null;
	
	private ConfirmationMailBuilderFactory() {
		super();
	}
	
	public static ConfirmationMailBuilderFactory getInstance() {
		if(factory == null) {
			factory = new ConfirmationMailBuilderFactory();
		}
		return factory;
	}
	
	public RenterConfirmationMailBuilder getRenterConfirmationMailBuilder() {
		return new RenterConfirmationMailBuilder();
	}
	
	public PMConfirmationMailBuilder getPMConfirmationBuilder() {
		return new PMConfirmationMailBuilder();
	}
}
