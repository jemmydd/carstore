package com.luoyanjie.mechanical.sys;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author luoyanjie
 * @date 2019-07-29 18:22
 * Coding happily every day!
 */
@Configuration
@EnableSwagger2
@Profile("test")
public class SwaggerConfig {
    @Bean
    public Docket demoApi() {
        /*return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luoyanjie.mechanical.web"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(
                        Lists.newArrayList(
                                new ParameterBuilder()
                                        .name("token")
                                        .description("鉴权用的token")
                                        .modelRef(new ModelRef("string"))
                                        .parameterType("query")
                                        .required(true)
                                        .build()
                        )
                );*/

        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = Lists.newArrayList();
        tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luoyanjie.mechanical.web"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("mechanical Project API")
                .description("any problem cloud ask back-end coder")
                .version("1.0")
                .termsOfServiceUrl("NO terms of service")
                .contact(new Contact("luoyanjie", "", ""))
                //.license("The Apache License, Version 2.0")
                //.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }


}
