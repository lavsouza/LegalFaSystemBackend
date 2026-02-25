package legalfasystem.infra.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class corsConfig {

    // URLs permitidas (configurável por ambiente)
    @Value("${cors.allowed-origins:http://localhost:5173}")
    private String allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // ✅ SEGURO: Apenas origins específicas (não usa "*")
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        
        // ✅ SEGURO: Apenas métodos necessários
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // ✅ SEGURO: Headers específicos (não usa "*")
        configuration.setAllowedHeaders(Arrays.asList(
            "Content-Type",
            "Authorization",
            "X-Requested-With",
            "Accept",
            "Origin"
        ));
        
        // ✅ SEGURO: Permite credenciais (necessário para cookies/auth)
        configuration.setAllowCredentials(true);
        
        // ✅ SEGURO: Expõe apenas headers necessários
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        
        // ✅ SEGURO: Cache de 1 hora para requisições preflight (reduz overhead)
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}