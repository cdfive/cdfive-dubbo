package com.cdfive.mp3.config;

import com.deepoove.swagger.dubbo.annotations.EnableDubboSwagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author cdfive
 */
@EnableDubboSwagger
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cdfive.mp3.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("mp3-service")
                .description("mp3-service description")
                .version("0.0.1")
                .build();
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(Environment environment, DocumentationCache documentationCache) {
        return new CustormSwaggerResourcesProvider(environment, documentationCache);
    }

    static class CustormSwaggerResourcesProvider extends InMemorySwaggerResourcesProvider {

        public CustormSwaggerResourcesProvider(Environment environment, DocumentationCache documentationCache) {
            super(environment, documentationCache);
        }

        @Override
        public List<SwaggerResource> get() {
            List<SwaggerResource> swaggerResources = super.get();

            SwaggerResource swaggerDubboResource = new SwaggerResource();
            swaggerDubboResource.setName("dubbo");
            swaggerDubboResource.setLocation("/swagger-dubbo/api-docs");
            swaggerDubboResource.setSwaggerVersion("2.0");
            swaggerResources.add(swaggerDubboResource);
            return swaggerResources;
        }
    }
}
