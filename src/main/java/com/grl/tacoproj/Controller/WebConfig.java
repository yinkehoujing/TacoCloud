package com.grl.tacoproj.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // as a java bean
public class WebConfig implements WebMvcConfigurer
{
    @Override // to override the Controllers instead of Controller
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("home");

    }
}
