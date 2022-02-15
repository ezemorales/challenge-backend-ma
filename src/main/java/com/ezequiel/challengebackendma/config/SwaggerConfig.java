package com.ezequiel.challengebackendma.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.ezequiel.challengebackendma.controllers"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(getApiInfo());
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
        "API Pizzeria",
        "API Rest que registra el alta de pedidos de una pizzeria.",
        "1.0",
        "",
        new Contact("Ezequiel Morales", "https://www.linkedin.com/in/ezequiel-morales-a9438b19b/",
            "ezequielmorales5@icloud.com"),
        "",
        "",
        Collections.emptyList()
    );
  }

}
