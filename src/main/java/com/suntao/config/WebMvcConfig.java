package com.suntao.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.suntao.controller.interceptor.CityInterceptor;
import com.suntao.controller.interceptor.CountryInterceptor;
import com.suntao.filter.ContentCachingRequestFilter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CityInterceptor()).addPathPatterns("/city/**");
        registry.addInterceptor(new CountryInterceptor()).addPathPatterns("/country/**");
    }

    @Bean
    public FilterRegistrationBean<ContentCachingRequestFilter> contentCachingRequestFilter() {
        FilterRegistrationBean<ContentCachingRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ContentCachingRequestFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
