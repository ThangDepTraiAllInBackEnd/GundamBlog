package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
//@EnableWebMvc
//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
public class GundamBlogApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(GundamBlogApplication.class, args);
	}
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		if (!registry.hasMappingForPattern("/assets/**")) {
//			registry.addResourceHandler("/assets/*").addResourceLocations("classpath:/assets/***");
//		}
//	}

}
