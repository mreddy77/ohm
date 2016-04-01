package com.iohm.service.impl;

import org.springframework.stereotype.Component;

import com.iohm.service.IOhmValueCalculator;

@Component
public class  IOhmValueCalculatorImpl  implements IOhmValueCalculator{

	@Override
	public int calculateOhmValue(String bandAcolor, String bandBColor, String bandCColor, String bandDColor) {
		// TODO: calculate based on rek
		// return 0;
		// for now just return hash length
		return (bandAcolor+bandBColor+bandCColor+bandDColor).length();
	}
	
	

}
