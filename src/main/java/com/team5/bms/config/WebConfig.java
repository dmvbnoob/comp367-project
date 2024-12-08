package com.team5.bms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.team5.bms.web.util.StringToInstantConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addConverters(FormatterRegistry registry) {
        registry.addConverter(new StringToInstantConverter());
    }
    
}
