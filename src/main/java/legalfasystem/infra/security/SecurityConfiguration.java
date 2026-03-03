package legalfasystem.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfiguration(
            SecurityFilter securityFilter,
            CorsConfigurationSource corsConfigurationSource
    ) {
        this.securityFilter = securityFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // API REST com JWT
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        // 🔓 Rotas públicas
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Rotas de empresa - temporariamente públicas para testes
                        .requestMatchers(HttpMethod.GET, "/api/empresas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/empresas/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/empresas/**").permitAll()
                        
                        // 🔐 NOVO: Rotas de usuário - REQUEREM AUTENTICAÇÃO
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/me").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/**").permitAll()
                        
                        // 🔐 Rotas de contratos - REQUEREM AUTENTICAÇÃO
                        .requestMatchers(HttpMethod.GET, "/api/contratos/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/contratos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/contratos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/contratos/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/contratos/**").authenticated()
                        
                        // 🔐 Rotas de empresa - ações específicas exigem autenticação
                        .requestMatchers(HttpMethod.DELETE, "/api/empresas/**").permitAll()
                        
                        // 🔐 Qualquer outra rota exige autenticação
                        .anyRequest().permitAll()
                )

                // 🚨 Filtro JWT
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    // 🔗 Hierarquia de roles
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(
                "ROLE_ADMIN > ROLE_GESTOR \n" +
                "ROLE_GESTOR > ROLE_ADVOGADO \n" +
                "ROLE_ADVOGADO > ROLE_ANALISTA \n" +
                "ROLE_ANALISTA > ROLE_ESTAGIARIO"
        );
        return hierarchy;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}