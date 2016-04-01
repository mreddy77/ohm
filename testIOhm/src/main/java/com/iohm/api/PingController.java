package com.iohm.api;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iohm.Constants;
import com.iohm.model.ResponseMessage;

@RestController
@RequestMapping(value = Constants.URI_API)
public class PingController{
	private static final Logger LOGGER = Logger.getLogger(PingController.class.getName());
	
	 
    /**
     * check if the network connecting is ok.
     * @return 
     */
    @RequestMapping(value=Constants.URI_PING, method=RequestMethod.GET)
    public ResponseEntity<ResponseMessage> ping() {    
    	LOGGER.info("######### PING Successful");
        return new ResponseEntity<>(ResponseMessage.info("connected"), HttpStatus.OK);
    }
    
}