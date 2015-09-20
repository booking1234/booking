/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import net.cbtltd.shared.Location;
import net.cbtltd.shared.Region;

public interface RegionMapper
extends AbstractMapper<Region> {
	Region readbylocation(Location action);
	Region readbyname(Region action);
	Region name(Region action);
}