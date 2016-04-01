package com.iohm.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);	
	    
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		logger.debug("<--- Inside authentication entry point --->");
		
        // this is very important for a REST based API login. 
        // WWW-Authenticate header should be set as FormBased , else browser will show login dialog with realm
        response.setHeader("WWW-Authenticate", "FormBased");
        response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
	}

}
