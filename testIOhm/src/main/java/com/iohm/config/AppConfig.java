package com.iohm.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import com.iohm.Constants;
import com.iohm.service.impl.CustomAuthenticationEntryPoint;
import com.iohm.service.impl.CustomAuthenticationProvider;
import com.iohm.service.impl.IOhmValueCalculatorImpl;

@Configuration
@ComponentScan(
    basePackageClasses = {Constants.class, IOhmValueCalculatorImpl.class, CustomAuthenticationProvider.class, CustomAuthenticationEntryPoint.class},
    
    excludeFilters = {
        @Filter(
            type = FilterType.ANNOTATION,
            value = {
                RestController.class,
                ControllerAdvice.class,
                Configuration.class
            }
        )
    }
)

@PropertySources(value = {@PropertySource("file:${app.config.dir}/app.properties")})
@EnableScheduling
public class AppConfig {
	

}
