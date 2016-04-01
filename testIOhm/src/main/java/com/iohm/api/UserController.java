package com.iohm.api;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iohm.Constants;

@RestController
@RequestMapping(value = Constants.URI_API)
public class UserController {
	
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
    
    /**
     * Get the authenticated user info.
     * 
     * @param principal
     * @return 
     */
    @RequestMapping("/login")
    public ResponseEntity<Map<String, Object>> user(Principal principal) {
    	LOGGER.log(Level.INFO, "Authentication Successful");
    	
        Map<String, Object> map = new HashMap<>();
        map.put("username", principal.getName());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
   
}