package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.CountryCode;
import net.cbtltd.shared.RegionCode;

public interface RegionCodeMapper {
	List<RegionCode> getRegionCodes();
}
