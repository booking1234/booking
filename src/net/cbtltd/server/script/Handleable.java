package net.cbtltd.server.script;

public interface Handleable {
	void readProducts();
	void readPrices();
	void readSchedules();
	void readAlerts();
	void readSpecials();
	void readLocations();
	void readDescriptions();
	void readImages();
}
