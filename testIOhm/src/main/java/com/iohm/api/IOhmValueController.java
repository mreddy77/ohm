package com.iohm.api;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iohm.Constants;
import com.iohm.model.IOhmParameters;
import com.iohm.service.impl.IOhmValueCalculatorImpl;

@RestController
@RequestMapping(value = Constants.URI_API)
public class IOhmValueController {
	
	@Inject
    private IOhmValueCalculatorImpl iOhmCalculator;
    
	@RequestMapping(value=Constants.URI_CALCULATE, method=RequestMethod.POST)
    public ResponseEntity<String> calculateOhmValue(@RequestBody IOhmParameters iOhmParameters) {
    
    	String bandAColor = iOhmParameters.getBandAColor();
    	String bandBColor = iOhmParameters.getBandBColor();
    	String bandCColor = iOhmParameters.getBandCColor();
    	String bandDColor = iOhmParameters.getBandDColor();
    	
    	int iOhmValue = iOhmCalculator.calculateOhmValue(bandAColor, bandBColor, bandCColor, bandDColor);
    	
    	return new ResponseEntity<>(String.valueOf(iOhmValue), HttpStatus.OK);
    }
    
    
	@RequestMapping(value=Constants.URI_CALCULATE, method=RequestMethod.GET)
    public ResponseEntity<String> calculateOhmValue(@RequestParam String bandAColor, 
    												@RequestParam String bandBColor, 
    												@RequestParam String bandCColor, 
    												@RequestParam String bandDColor) {
    	
    	int iOhmValue = iOhmCalculator.calculateOhmValue(bandAColor, bandBColor, bandCColor, bandDColor);
    	
    	return new ResponseEntity<>(String.valueOf(iOhmValue), HttpStatus.OK);
    }

}
