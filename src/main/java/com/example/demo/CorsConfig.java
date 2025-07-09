//package com.example.demo;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class CorsConfig implements WebMvcConfigurer {

//    public void addCorsMapping(CorsRegistry registry){
//        registry.addMapping("/**")
//                .allowedOrigins(
//                        "http://127.0.0.1:5500",
//                        "http://localhost:5500"
//                )
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite os métodos HTTP comuns
//                .allowedHeaders("*") // Permite todos os cabeçalhos (Authorization, Content-Type, etc.)
//                .allowCredentials(true) // Se você usa cookies ou tokens JWT no cabeçalho Authorization
//                .maxAge(3600); // Tempo para o navegador armazenar em cache a resposta do preflight (1 hora)
//    }
//}
