package net.cbtltd.rest.jnuit;

import net.cbtltd.rest.AbstractLocation;
import net.cbtltd.rest.Items;

public class LocationFeedGenerator extends AbstractLocation {
	private static LocationFeedGenerator locationFeedGenerator;
	public LocationFeedGenerator() {}
	
	public static LocationFeedGenerator getInstance() {
		if (locationFeedGenerator == null) {
			locationFeedGenerator = new LocationFeedGenerator();
		}
		return locationFeedGenerator;
	}
	
	public Items getNearIDs (
			Double latitude,
			Double longitude,
			Double distance,
			String unit,
			String xsl) {
		return getNameIdsNearTo(latitude, longitude, distance, unit, xsl);
	}
}
