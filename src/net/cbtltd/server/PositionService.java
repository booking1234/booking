/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.server.geodesy.Ellipsoid;
import net.cbtltd.server.geodesy.GeodeticCalculator;
import net.cbtltd.server.geodesy.GeodeticCurve;
import net.cbtltd.server.geodesy.GeodeticMeasurement;
import net.cbtltd.server.geodesy.GlobalCoordinates;
import net.cbtltd.server.geodesy.GlobalPosition;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Unit;

/** The Class PositionService is to calculate distances and angles between positions on the Earth's surface. */
public class PositionService {

	/**
	 * Gets the distance between two locations.
	 *
	 * @param from the first location ID.
	 * @param to the second location ID.
	 * @return conversion to UN/CEFACT unit from metres
	 */
	public Double distance(Location from, Location to){
		if (from.noLatLng() || to.noLatLng()){return 100000000.0;}
		return distance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
	}

	/**
	 * Gets the ISO distance unit from the specified OTA distance unit.
	 *
	 * @param otaUnit the specified OTA distance unit.
	 * @return the equivalent UN/CEFACT unit.
	 */
	public static String isoUnit(String otaUnit){
		if (otaUnit == null) {return "KMT";}
		else if (otaUnit.equalsIgnoreCase("4")){return Unit.MMT;} // millimetre
		else if (otaUnit.equalsIgnoreCase("3")){return Unit.MTR;} // metre
		else if (otaUnit.equalsIgnoreCase("2")){return Unit.KMT;} // kilometre
		else if (otaUnit.equalsIgnoreCase("8")){return Unit.INH;} // inch
		else if (otaUnit.equalsIgnoreCase("7")){return Unit.FOT;} // foot
		else if (otaUnit.equalsIgnoreCase("6")){return Unit.YRD;} // yard
		else if (otaUnit.equalsIgnoreCase("1")){return Unit.SMI;} // statute mile
		else if (otaUnit.equalsIgnoreCase("9")){return Unit.NMI;} // nautical mile
		else return "KMT";
	}

	/*
	 * Gets the conversion factor from the specified UN/CEFACT unit to metres.
	 * 
	 * @param unit the specified UN/CEFACT unit.
	 * @return conversion factor to metres from the UN/CEFACT unit.
	 */
	private static double factor(String unit){
		if (unit == null){return 0.001;}
		else if (unit.equalsIgnoreCase(Unit.MMT)){ return 1000.0;} // millimetre
		else if (unit.equalsIgnoreCase(Unit.MTR)){ return 1.0;} // metre
		else if (unit.equalsIgnoreCase(Unit.KMT)){ return 0.001;} // kilometre
//		else if (unit.equalsIgnoreCase(Unit.KTM)){ return 0.001;} // kilometre
		else if (unit.equalsIgnoreCase(Unit.INH)){ return 39.3700787;} // inch
		else if (unit.equalsIgnoreCase(Unit.FOT)){ return 3.2808399;} // foot
		else if (unit.equalsIgnoreCase(Unit.YRD)){ return 1.0936133;} // yard
		else if (unit.equalsIgnoreCase(Unit.SMI)){ return 0.000621371192;} // statute mile
		else if (unit.equalsIgnoreCase(Unit.NMI)){ return 0.000539956803;} // nautical mile
		return 0.001;
	}

	/**
	 * Gets the global coordinates of a destination given the origin, distance in metres, and bearing.
	 *
	 * @param latitude the latitude of the origin.
	 * @param longitude the longitude of the origin.
	 * @param bearing in degrees from the origin.
	 * @param distance the distance from the origin.
	 * @return the global coordinates of the destination.
	 */
	public static GlobalCoordinates destination(double latitude, double longitude, double bearing, double distance) {
		return destination(latitude, longitude, bearing, distance, Unit.MTR, Ellipsoid.WGS84);
	}

	/**
	 * Gets the global coordinates of a destination given the origin, distance, unit and bearing.
	 *
	 * @param latitude the latitude of the origin.
	 * @param longitude the longitude of the origin.
	 * @param bearing in degrees from the origin.
	 * @param distance the distance from the origin.
	 * @param unit the unit of measure of the distance.
	 * @return the global coordinates of the destination.
	 */
	public static GlobalCoordinates destination(double latitude, double longitude, double bearing, double distance, String unit) {
		return destination(latitude, longitude, bearing, distance, unit, Ellipsoid.WGS84);
	}

	/*
	 * Gets the global coordinates of a destination given the origin, distance, unit, bearing and ellipsoid.
	 *
	 * @param latitude the latitude of the origin.
	 * @param longitude the longitude of the origin.
	 * @param bearing in degrees from the origin.
	 * @param distance the distance from the origin.
	 * @param unit the unit of measure of the distance.
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @return the global coordinates of the destination.
	 */
	private static GlobalCoordinates destination (
			double latitude,
			double longitude,
			double bearing,
			double distance,
			String unit,
			Ellipsoid ellipsoid) {
		return destination(new GlobalCoordinates(latitude, longitude), bearing, distance, unit, ellipsoid);
	}

	/*
	 * Gets the global coordinates of a destination given the global coordinates of the origin, distance, unit, bearing and ellipsoid.
	 *
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @param from the global coordinates of the origin.
	 * @param longitude the longitude of the origin.
	 * @param bearing in degrees from the origin.
	 * @param distance the distance from the origin.
	 * @param unit the unit of measure of the distance.
	 * @return the global coordinates of the destination.
	 */
	private static GlobalCoordinates destination (
			GlobalCoordinates from,
			double bearing,
			double distance,
			String unit,
			Ellipsoid ellipsoid) {
		GeodeticCalculator geoCalc = new GeodeticCalculator();
		return geoCalc.calculateEndingGlobalCoordinates(ellipsoid, from, bearing, distance / factor(unit));
	}

	/**
	 * Gets the distance in metres between two sets of latitude and longitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @return the distance between the points.
	 */
	public static double distance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
		return distance(fromLatitude, fromLongitude, toLatitude, toLongitude, "MTR", Ellipsoid.WGS84);
	}

	/**
	 * Gets the distance in the specified unit between two sets of latitude and longitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @param unit the unit of measure of the distance.
	 * @return the distance between the points.
	 */
	public static double distance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude, String unit) {
		return distance(fromLatitude, fromLongitude, toLatitude, toLongitude, unit, Ellipsoid.WGS84);
	}

	/*
	 * Gets the distance in metres between two sets of latitude and longitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @param unit the unit of measure of the distance.
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @return the distance between the points.
	 */
	private static double distance(
			double fromLatitude,
			double fromLongitude,
			double toLatitude,
			double toLongitude,
			String unit,
			Ellipsoid ellipsoid) {
		GeodeticCalculator geoCalc = new GeodeticCalculator();		// instantiate the calculator
		GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(ellipsoid, new GlobalCoordinates(fromLatitude, fromLongitude), new GlobalCoordinates(toLatitude, toLongitude));
		return geoCurve.getEllipsoidalDistance() / factor(unit);
	}

	/**
	 * Gets the bearing in degrees between two sets of latitude and longitude.
	 *
	 * @param fromLatitude the from latitude
	 * @param fromLongitude the from longitude
	 * @param toLatitude the to latitude
	 * @param toLongitude the to longitude
	 * @return the azimuth between two points
	 */
	public static double azimuth(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
		return azimuth(fromLatitude, fromLongitude, toLatitude, toLongitude, Ellipsoid.WGS84);
	}

	/*
	 * Gets the bearing in degrees between two sets of latitude and longitude.
	 *
	 * @param fromLatitude the from latitude
	 * @param fromLongitude the from longitude
	 * @param toLatitude the to latitude
	 * @param toLongitude the to longitude
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @return the azimuth between two points
	 */
	private static double azimuth(
			double fromLatitude,
			double fromLongitude,
			double toLatitude,
			double toLongitude,
			Ellipsoid ellipsoid) {
		GeodeticCalculator geoCalc = new GeodeticCalculator();		// instantiate the calculator
		GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(ellipsoid, new GlobalCoordinates(fromLatitude, fromLongitude), new GlobalCoordinates(toLatitude, toLongitude));
		return geoCurve.getAzimuth();
	}

	/**
	 * Gets the reverse bearing in degrees between two sets of latitude and longitude.
	 *
	 * @param fromLatitude the from latitude
	 * @param fromLongitude the from longitude
	 * @param toLatitude the to latitude
	 * @param toLongitude the to longitude
	 * @return the azimuth between two points
	 */
	public static double reverseAzimuth(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
		return reverseAzimuth(fromLatitude, fromLongitude, toLatitude, toLongitude, Ellipsoid.WGS84);
	}

	/*
	 * Gets the reverse bearing in degrees between two sets of latitude and longitude.
	 *
	 * @param fromLatitude the from latitude
	 * @param fromLongitude the from longitude
	 * @param toLatitude the to latitude
	 * @param toLongitude the to longitude
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @return the azimuth between two points
	 */
	private static double reverseAzimuth(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude, Ellipsoid ellipsoid){
		GeodeticCalculator geoCalc = new GeodeticCalculator();		// instantiate the calculator
		GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(ellipsoid, new GlobalCoordinates(fromLatitude, fromLongitude), new GlobalCoordinates(toLatitude, toLongitude));
		return geoCurve.getReverseAzimuth();
	}

	/**
	 * Gets the distance in metres between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param fromAltitude the from altitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @param toAltitude the to altitude.
	 * @return the three-dimensional distance between two points and elevations.
	 */
	public static double distance(double fromLatitude, double fromLongitude, double fromAltitude, double toLatitude, double toLongitude, double toAltitude) {
		return distance(fromLatitude, fromLongitude, fromAltitude, toLatitude, toLongitude, toAltitude, "MTR", Ellipsoid.WGS84);
	}

	/**
	 * Gets the distance in the specified unit between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param fromAltitude the from altitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @param toAltitude the to altitude.
	 * @return the three-dimensional distance between two points and elevations.
	 */
	public static double distance(double fromLatitude, double fromLongitude, double fromAltitude, double toLatitude, double toLongitude, double toAltitude, String unit) {
		return distance(fromLatitude, fromLongitude, fromAltitude, toLatitude, toLongitude, toAltitude, unit, Ellipsoid.WGS84);
	}

	/*
	 * Gets the distance in the specified unit between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param fromAltitude the from altitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @param toAltitude the to altitude.
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @return the three-dimensional distance between two points and elevations.
	 */
	private static double distance(
			double fromLatitude,
			double fromLongitude,
			double fromAltitude,
			double toLatitude,
			double toLongitude,
			double toAltitude,
			String unit,
			Ellipsoid ellipsoid) {
		GeodeticCalculator geoCalc = new GeodeticCalculator();
		GeodeticMeasurement geoMeasurement = geoCalc.calculateGeodeticMeasurement(
				ellipsoid,
				new GlobalPosition(fromLatitude, fromLongitude, fromAltitude),
				new GlobalPosition(toLatitude, toLongitude, toAltitude));

		return geoMeasurement.getPointToPointDistance() / factor(unit);
	}

	/**
	 * Gets the vertical distance in metres between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param fromAltitude the from altitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @param toAltitude the to altitude.
	 * @return the vertical distance between two points and elevations.
	 */
	public static double verticalDistance(double fromLatitude, double fromLongitude, double fromAltitude, double toLatitude, double toLongitude, double toAltitude) {
		return verticalDistance(fromLatitude, fromLongitude, fromAltitude, toLatitude, toLongitude, toAltitude, "MTR", Ellipsoid.WGS84);
	}

	/**
	 * Gets the vertical distance in the specified unit between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param fromAltitude the from altitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @param toAltitude the to altitude.
	 * @return the vertical distance between two points and elevations.
	 */
	public static double verticalDistance(double fromLatitude, double fromLongitude, double fromAltitude, double toLatitude, double toLongitude, double toAltitude, String unit) {
		return verticalDistance(fromLatitude, fromLongitude, fromAltitude, toLatitude, toLongitude, toAltitude, unit, Ellipsoid.WGS84);
	}

	/*
	 * Gets the vertical distance in the specified unit between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude.
	 * @param fromLongitude the from longitude.
	 * @param fromAltitude the from altitude.
	 * @param toLatitude the to latitude.
	 * @param toLongitude the to longitude.
	 * @param toAltitude the to altitude.
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @return the vertical distance between two points and elevations.
	 */
	private static double verticalDistance(
			double fromLatitude,
			double fromLongitude,
			double fromAltitude,
			double toLatitude,
			double toLongitude,
			double toAltitude,
			String unit,
			Ellipsoid ellipsoid) {
		GeodeticCalculator geoCalc = new GeodeticCalculator();
		GeodeticMeasurement geoMeasurement = geoCalc.calculateGeodeticMeasurement(
				ellipsoid,
				new GlobalPosition(fromLatitude, fromLongitude, fromAltitude),
				new GlobalPosition(toLatitude, toLongitude, toAltitude));

		return geoMeasurement.getElevationChange() / factor(unit);
	}

	/**
	 * Gets the bearing in degrees between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude
	 * @param fromLongitude the from longitude
	 * @param fromAltitude the from altitude
	 * @param toLatitude the to latitude
	 * @param toLongitude the to longitude
	 * @param toAltitude the to altitude
	 * @return the azimuth between two points and elevations
	 */
	public static double azimuth(double fromLatitude, double fromLongitude, double fromAltitude, double toLatitude, double toLongitude, double toAltitude) {
		return azimuth(fromLatitude, fromLongitude, fromAltitude, toLatitude, toLongitude, toAltitude, Ellipsoid.WGS84);
	}

	/*
	 * Gets the bearing in degrees between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude
	 * @param fromLongitude the from longitude
	 * @param fromAltitude the from altitude
	 * @param toLatitude the to latitude
	 * @param toLongitude the to longitude
	 * @param toAltitude the to altitude
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @return the azimuth between two points and elevations
	 */
	private static double azimuth(
			double fromLatitude,
			double fromLongitude,
			double fromAltitude,
			double toLatitude,
			double toLongitude,
			double toAltitude,
			Ellipsoid ellipsoid) {
		GeodeticCalculator geoCalc = new GeodeticCalculator();		// instantiate the calculator
		GeodeticMeasurement geoMeasurement = geoCalc.calculateGeodeticMeasurement(
				ellipsoid,
				new GlobalPosition(fromLatitude, fromLongitude, fromAltitude),
				new GlobalPosition(toLatitude, toLongitude, toAltitude));
		return geoMeasurement.getAzimuth();
	}

	/**
	 * Gets the reverse bearing in degrees between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude
	 * @param fromLongitude the from longitude
	 * @param fromAltitude the from altitude
	 * @param toLatitude the to latitude
	 * @param toLongitude the to longitude
	 * @param toAltitude the to altitude
	 * @return the azimuth between two points and elevations
	 */
	public static double reverseAzimuth(double fromLatitude, double fromLongitude, double fromAltitude, double toLatitude, double toLongitude, double toAltitude) {
		return reverseAzimuth(fromLatitude, fromLongitude, fromAltitude, toLatitude, toLongitude, toAltitude, Ellipsoid.WGS84);
	}

	/*
	 * Gets the reverse bearing in degrees between two sets of latitude, longitude and altitude.
	 *
	 * @param fromLatitude the from latitude
	 * @param fromLongitude the from longitude
	 * @param fromAltitude the from altitude
	 * @param toLatitude the to latitude
	 * @param toLongitude the to longitude
	 * @param toAltitude the to altitude
	 * @param ellipsoid the standard ellipsoid to be used in the calculation.
	 * @return the azimuth between two points and elevations
	 */
	private static double reverseAzimuth(
			double fromLatitude,
			double fromLongitude,
			double fromAltitude,
			double toLatitude,
			double toLongitude,
			double toAltitude,
			Ellipsoid ellipsoid) {
		GeodeticCalculator geoCalc = new GeodeticCalculator();		// instantiate the calculator
		GeodeticMeasurement geoMeasurement = geoCalc.calculateGeodeticMeasurement(
				ellipsoid,
				new GlobalPosition(fromLatitude, fromLongitude, fromAltitude),
				new GlobalPosition(toLatitude, toLongitude, toAltitude));
		return geoMeasurement.getReverseAzimuth();
	}
}
