package com.sunl19ht.config;

import com.sunl19ht.interceptor.JwtTokenAdminInterceptor;
import com.sunl19ht.interceptor.JwtTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/api/employee/*")
                .excludePathPatterns("/api/employee/login", "/api/user/slideshow", "/api/user/order/orderStatus");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/api/user/**")
                .addPathPatterns("/api/user/order/my")
                .addPathPatterns("/api/user/order/**")
                .addPathPatterns("/api/pay/**")
                .addPathPatterns("/api/useCar/**")
                .excludePathPatterns("/api/user/login", "/api/user/slideshow", "/api/user/order/orderStatus");
    }

    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowCredentials(false)
                .allowedMethods("*")
                .maxAge(3600);

    }
}
