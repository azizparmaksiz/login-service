package com.eteshis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.eteshis.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(
                        newArrayList(new ParameterBuilder()
                                .name("Authorization")
                                .description("Authorization Header")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .build()));
    }
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Eteshis API",
                "Eteshis API for Online fast diagnosing disease",
                "1.0",
                "Terms of service",
                new Contact("Nuri Özalp", "https://eteshis.com", ""),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0", Collections.<VendorExtension>emptyList());
        return apiInfo;
    }
}