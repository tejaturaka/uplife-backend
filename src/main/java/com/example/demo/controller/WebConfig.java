////////////////package com.example.demo.controller;  // <--- CHANGE THIS LINE TO MATCH THE FOLDER
////////////////
////////////////import org.springframework.context.annotation.Configuration;
////////////////import org.springframework.web.servlet.config.annotation.CorsRegistry;
////////////////import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
////////////////
////////////////@Configuration
////////////////public class WebConfig implements WebMvcConfigurer {
////////////////
////////////////    @Override
////////////////    public void addCorsMappings(CorsRegistry registry) {
////////////////        registry.addMapping("/**")
////////////////                .allowedOrigins(
////////////////                    "https://uplife-frontend.vercel.app",
////////////////                    "http://localhost:5173"
////////////////                )
////////////////                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
////////////////                .allowedHeaders("*")
////////////////                .allowCredentials(true);
////////////////    }
////////////////}
//////////////
//////////////package com.example.demo.controller;
//////////////
//////////////import org.springframework.context.annotation.Configuration;
//////////////import org.springframework.web.servlet.config.annotation.CorsRegistry;
//////////////import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//////////////
//////////////@Configuration
//////////////public class WebConfig implements WebMvcConfigurer {
//////////////
//////////////    @Override
//////////////    public void addCorsMappings(CorsRegistry registry) {
//////////////        registry.addMapping("/**")
//////////////                .allowedOrigins(
//////////////                    "https://uplife-frontend.vercel.app",
//////////////                    "http://localhost:5173"
//////////////                )
//////////////                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//////////////                .allowedHeaders("*")
//////////////                .allowCredentials(true);
//////////////    }
//////////////}
////////////
////////////
////////////
////////////
////////////
////////////
////////////package com.example.demo.controller;
////////////
////////////import org.springframework.context.annotation.Bean;
////////////import org.springframework.context.annotation.Configuration;
////////////import org.springframework.web.cors.CorsConfiguration;
////////////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
////////////import org.springframework.web.filter.CorsFilter;
////////////
////////////import java.util.Arrays;
////////////import java.util.List;
////////////
////////////@Configuration
////////////public class WebConfig {
////////////
////////////    @Bean
////////////    public CorsFilter corsFilter() {
////////////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////////////        CorsConfiguration config = new CorsConfiguration();
////////////        
////////////        // 1. Allow your Vercel Frontend and Localhost
////////////        config.setAllowedOrigins(List.of(
////////////            "https://uplife-frontend.vercel.app",
////////////            "http://localhost:5173"
////////////        ));
////////////        
////////////        // 2. Allow specific headers and methods to prevent blocking
////////////        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
////////////        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
////////////        
////////////        // 3. Allow credentials
////////////        config.setAllowCredentials(true);
////////////        
////////////        source.registerCorsConfiguration("/**", config);
////////////        return new CorsFilter(source);
////////////    }
////////////}
//////////
//////////
//////////
//////////package com.example.demo.controller;
//////////
//////////import org.springframework.context.annotation.Bean;
//////////import org.springframework.context.annotation.Configuration;
//////////import org.springframework.web.cors.CorsConfiguration;
//////////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//////////import org.springframework.web.filter.CorsFilter;
//////////
//////////import java.util.Arrays;
//////////import java.util.List;
//////////
//////////@Configuration
//////////public class WebConfig {
//////////
//////////    @Bean
//////////    public CorsFilter corsFilter() {
//////////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//////////        CorsConfiguration config = new CorsConfiguration();
//////////        
//////////        // 1. EXACTLY ALLOW YOUR VERCEL DOMAIN
//////////        config.setAllowedOrigins(Arrays.asList(
//////////            "https://uplife-frontend.vercel.app", 
//////////            "http://localhost:5173"
//////////        ));
//////////        
//////////        // 2. ALLOW CREDENTIALS & HEADERS
//////////        config.setAllowCredentials(true);
//////////        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
//////////        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//////////        
//////////        source.registerCorsConfiguration("/**", config);
//////////        return new CorsFilter(source);
//////////    }
//////////}
////////
////////
////////package com.example.demo.controller;
////////
////////import org.springframework.context.annotation.Configuration;
////////import org.springframework.web.servlet.config.annotation.CorsRegistry;
////////import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
////////
////////@Configuration
////////public class WebConfig implements WebMvcConfigurer {
////////
////////    @Override
////////    public void addCorsMappings(CorsRegistry registry) {
////////        registry.addMapping("/**")
////////                // This allows Vercel, Localhost, or ANY other domain to connect
////////                .allowedOriginPatterns("*") 
////////                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
////////                .allowedHeaders("*")
////////                .allowCredentials(true);
////////    }
////////}
//////
//////
//////package com.example.demo.controller;
//////
//////import org.springframework.context.annotation.Bean;
//////import org.springframework.context.annotation.Configuration;
//////import org.springframework.web.cors.CorsConfiguration;
//////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//////import org.springframework.web.filter.CorsFilter;
//////
//////import java.util.Arrays;
//////import java.util.List;
//////
//////@Configuration
//////public class WebConfig {
//////
//////    @Bean
//////    public CorsFilter corsFilter() {
//////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//////        CorsConfiguration config = new CorsConfiguration();
//////        
//////        // 1. ALLOW YOUR VERCEL FRONTEND
//////        config.setAllowedOrigins(Arrays.asList(
//////            "https://uplife-frontend.vercel.app", 
//////            "http://localhost:5173"
//////        ));
//////        
//////        // 2. ALLOW CREDENTIALS & HEADERS
//////        config.setAllowCredentials(true);
//////        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
//////        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//////        
//////        source.registerCorsConfiguration("/**", config);
//////        return new CorsFilter(source);
//////    }
//////}
////
////
////
////
////package com.example.demo.controller;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.web.cors.CorsConfiguration;
////import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
////import org.springframework.web.filter.CorsFilter;
////
////import java.util.Arrays;
////import java.util.List;
////
////@Configuration
////public class WebConfig {
////
////    @Bean
////    public CorsFilter corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////        
////        // 1. Explicitly allow your Vercel Frontend & Localhost
////        config.setAllowedOrigins(Arrays.asList(
////            "https://uplife-frontend.vercel.app", 
////            "http://localhost:5173"
////        ));
////        
////        // 2. Allow Credentials (Cookies/Auth headers)
////        config.setAllowCredentials(true);
////        
////        // 3. Allow all standard methods and headers
////        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With"));
////        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
////        
////        source.registerCorsConfiguration("/**", config);
////        return new CorsFilter(source);
////    }
////}
//
//
//
//package com.example.demo.controller;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class WebConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        
//        // 1. Explicitly allow your Vercel Frontend & Localhost
//        config.setAllowedOrigins(Arrays.asList(
//            "https://uplife-frontend.vercel.app", 
//            "http://localhost:5173"
//        ));
//        
//        // 2. Allow Credentials (Cookies/Auth headers)
//        config.setAllowCredentials(true);
//        
//        // 3. Allow all standard headers
//        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With"));
//        
//        // 4. Allow all standard methods
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
//        
//        // Apply to all paths
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}


package com.example.demo.controller;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 1. ALLOW CREDENTIALS
        config.setAllowCredentials(true);
        
        // 2. ALLOW YOUR SPECIFIC DOMAINS
        config.setAllowedOrigins(Arrays.asList(
            "https://uplife-frontend.vercel.app", 
            "http://localhost:5173"
        ));
        
        // 3. ALLOW EVERYTHING ELSE
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization", "X-Requested-With"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        source.registerCorsConfiguration("/**", config);
        
        // 4. FORCE THIS FILTER TO RUN FIRST
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}