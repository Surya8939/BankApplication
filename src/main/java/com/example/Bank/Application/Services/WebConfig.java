package com.example.Bank.Application.Services;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Apply CORS settings to all endpoints
						.allowedOrigins("http://localhost:3000") // Your frontend URL
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("Authorization", "Content-Type", "Accept", "X-Requested-With")
						.allowCredentials(true) // Allow cookies/credentials (tokens)
						.maxAge(3600); // Cache preflight requests for 1 hour
			}
		};
	}
}
