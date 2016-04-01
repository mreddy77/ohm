package com.iohm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger LOGGER = Logger.getLogger(CustomAuthenticationProvider.class.getName());
	
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	LOGGER.log(Level.INFO, "####### Authenticating the user::");
    	
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        try
		{
        	LOGGER.log(Level.INFO, "####### Auth User::"+ name);
        	// do some custom stuff here
        	// either go to ldap or db to verify creds
        	// for now just admin/iohm
        	if (name.equals("admin") && password.equals("iohm")) {
                List<GrantedAuthority> grantedAuths = new ArrayList<>();
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
                LOGGER.log(Level.INFO, "####### Authenticated as admin");
                return auth;
        	}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
        return null;  
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}