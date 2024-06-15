package ras.demo.camunda.configuration;

import jakarta.servlet.Filter;
import org.camunda.bpm.engine.rest.security.auth.ProcessEngineAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CamundaRestAuthConfig {

    @Bean
    public FilterRegistrationBean<Filter> configureEngineAuthenticationFilter() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();

        filter.setFilter(new ProcessEngineAuthenticationFilter());
        filter.setName("camunda-auth");
        filter.addInitParameter("authentication-provider",
                "org.camunda.bpm.engine.rest.security.auth.impl.HttpBasicAuthenticationProvider");
        filter.addUrlPatterns("/engine-rest/*");
        filter.setOrder(101);

        return filter;
    }

    @Bean
    public FilterRegistrationBean<Filter> processCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>(new CorsFilter(source));
        filter.setOrder(0);

        return filter;
    }
}