package com.teachhub.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                .allowedMethods("GET","POST","DELETE","PUT","PATCH","OPTIONS")
                .allowedOrigins("http://localhost:5173")
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}
