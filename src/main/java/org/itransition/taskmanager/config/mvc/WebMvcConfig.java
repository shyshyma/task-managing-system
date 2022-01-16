package org.itransition.taskmanager.config.mvc;

import org.itransition.commons.web.HttpHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ControllerLoggingHandlerInterceptor());
    }

    @Bean
    public HttpHelper httpHelper() {
        return new HttpHelper();
    }
}
