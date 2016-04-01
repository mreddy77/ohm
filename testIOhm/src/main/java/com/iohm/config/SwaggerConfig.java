package com.iohm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
// Loads the spring beans required by the framework
@Profile(value = {"dev", "staging"})
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("private-api")
            .apiInfo(apiInfo())
            .select()
            //.paths(postPaths())
            .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Ohm UI")
            .description("Ohm Simple UI")
            .termsOfServiceUrl("")
            .version("1.0.0")
            .build();
    }

}
