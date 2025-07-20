package com.company.employeemanagement.config;

import com.company.employeemanagement.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(){
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
    
        registrationBean.setFilter(new AuthenticationFilter());
        // Apply filter only to secure URLs.
        registrationBean.addUrlPatterns("/admin/*", "/employee/*");
    
        return registrationBean;
    }
}