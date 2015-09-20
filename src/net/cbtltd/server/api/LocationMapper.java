/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.Area;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Position;
import net.cbtltd.shared.location.LocationPosition;

public interface LocationMapper
extends AbstractMapper<Location> {
	Location exists(Location action);
	Location pmssearch(Location action);
	Location interhomeSearch(Location action);
	Location rentalsunitedSearch(Location action);
	Location name(Location action);
	ArrayList<Location> readByGName();
	ArrayList<Location> readByUSA();
	ArrayList<Location> googleExists(Location action);
	int countByName(String name, String country);
	ArrayList<NameId> countrynameid(NameIdAction action);
	ArrayList<NameId> nameidbycountryregion(NameIdAction action); //NameCountrySubdivId
	ArrayList<NameId> nameidbycountry(NameIdAction action); //LocationNameIdByCountry
	ArrayList<NameId> regionnameid(NameIdAction action); //SubdivisionNameIdByCountry
	ArrayList<NameId> nameidnearby(Area action); //Nearby
	Position position(LocationPosition action); //Coordinate
	ArrayList<String> nearby(Area action);
	ArrayList<Location> activeLocations ();
	//REST SQL queries
	net.cbtltd.rest.Location restread(String locationid);
	ArrayList<net.cbtltd.shared.Location> getSearchLocations(NameId nameid); // get locations by term (name) and pos (channel partner id) 
	ArrayList<net.cbtltd.shared.Location> getSearchSublocations(NameId nameid); // get locations and sublocations by term (name) and pos (channel partner id)
	
	//scripts
	ArrayList<Location> getUnUpdatedEntries();
}