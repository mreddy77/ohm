package com.iohm.service;

/**
 * 
 * @author mreddy
 *
 */
public interface IOhmValueCalculator {
	
	/**
	 * Calculate an ohm value based on band color
	 * @param bandAcolor
	 * @param bandBColor
	 * @param bandCColor
	 * @param bandDColor
	 * @return, an ohm value
	 */
	int calculateOhmValue(String bandAcolor, String bandBColor, String bandCColor, String bandDColor);

}
