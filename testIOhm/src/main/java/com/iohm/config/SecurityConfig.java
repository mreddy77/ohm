package com.iohm.config;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.iohm.service.impl.CustomAuthenticationEntryPoint;
import com.iohm.service.impl.CustomAuthenticationProvider;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;
	
    @Inject
    private CustomAuthenticationProvider customAuthenticationProvider;
    
    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class.getName());


	@Override
	public void configure(WebSecurity web) throws Exception {
		web
            .ignoring()
			.antMatchers("/**/*.html", //
                         "/css/**", //
                         "/js/**", //
                         "/i18n/**",// 
                         "/libs/**",//
                         "/img/**", //
                         "/webjars/**",//
                         "/ico/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		    http   
                .authorizeRequests()   
    			.antMatchers("/api/ping")
    			.permitAll()
    		.and()
                .authorizeRequests()   
    			.antMatchers("/api/**")
    			.authenticated()
    		.and()
    			.authorizeRequests()   
    			.anyRequest()
    			.permitAll()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                    .csrf()
                    .disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);		
	}


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Bean
	public PlaintextPasswordEncoder passwordEncoder() {
		PlaintextPasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
		return passwordEncoder;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProviders) {
		return new ProviderManager(authenticationProviders);
	}
	

}
