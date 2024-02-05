package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.annotations.security.SecurityScheme;





@Configuration
@SecurityScheme(
		
		name="scheme1",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme="bearer"
		
		
		)
@OpenAPIDefinition(
		info=@Info(
				title="Blog App Apis",
				description="This is backend of blog app project ",
				version="1.0V",				contact=@Contact(
						name="Abhishek Gore",
						email="abhishekgore211@gmail.com",
						url="https://abhigore.github.io/Portfolio/"
						
						), license = @License(
								name="Open Licence ",
								url="https://abhigore.github.io/Portfolio/"
								)
				)
				, 
				   externalDocs = @ExternalDocumentation(
						   description = "This is external docs ",
						   url="https://abhigore.github.io/Portfolio/"
						   
						   
						   )
		
		)
public class SwaggerConfig {

//	@Bean
//	public OpenAPI usersMicroserviceOpenAPI() {
//		
//		String schemeName=" barerScheme";
//		return new OpenAPI()
////        		.components(new Components().addSecuritySchemes(schemeName, new SecurityScheme()
////        				.name(schemeName)
////        				.type(SecuritySchemeType.HTTP)
////        				.bearerFormat("JWT")
////        				.scheme("bearer")
////        				
////        				
////        				)
////        				
////        				)
//                .info(new Info().title("Blogging Application ")
//                                 .description("This project is  developed by Abhishek Gore  ")
//                                 .version("1.0"));
//}
	}

