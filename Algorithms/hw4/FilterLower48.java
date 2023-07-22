package zdszeto.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.GPS;

/**
 * Given a country (such as "United States") and a GPS location of an airport, return TRUE
 * if this airport should be included.
 * 
 * COPY this file into your USERID.hw4 package and modify it accordingly.
 */
public class FilterLower48 implements FilterAirport {

	@Override
	public boolean accept(String country, GPS gps) {
		return gps.longitude >= -124.41 && gps.longitude <= -66.9499 && gps.latitude >= 24.5231 && gps.latitude <= 46.29204 && country.contentEquals("United States");
		
	}
}
