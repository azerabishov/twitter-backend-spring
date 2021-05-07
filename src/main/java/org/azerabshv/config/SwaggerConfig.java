package org.azerabshv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
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
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.azerabshv"))
                .paths(PathSelectors.any())
                .build();
//                .apiInfo(apiInfo());

    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Twitter clone")
                .description("An application that covers the core features of twitter")
                .version("1.0")
                .contact(new Contact("Azer Abishov", null, "azer.abshv@gmail.com"))
                .build();

    }

}
