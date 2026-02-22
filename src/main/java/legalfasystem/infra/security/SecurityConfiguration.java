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

    public SecurityConfiguration(SecurityFilter securityFilter, CorsConfigurationSource corsConfigurationSource) {
        this.securityFilter = securityFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_GESTOR \n " +
                "ROLE_GESTOR > ROLE_ADVOGADO \n " +
                "ROLE_ADVOGADO > ROLE_ANALISTA \n " +
                "ROLE_ANALISTA > ROLE_ESTAGIARIO";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))  // ← ADICIONE
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Rotas Públicas (sem autenticação)
                        .requestMatchers(HttpMethod.POST, "/api/empresas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // ← ADICIONE (preflight CORS)

                        // Rotas Protegidas
                        .requestMatchers(HttpMethod.GET, "/api/empresas/**").hasAnyRole("ADMIN", "GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/empresas/**").hasAnyRole("ADMIN", "GESTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/empresas/**").hasRole("ADMIN")

                        
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



/* Prod version
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

    public SecurityConfiguration(SecurityFilter securityFilter, CorsConfigurationSource corsConfigurationSource) {
        this.securityFilter = securityFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_GESTOR \n " +
                "ROLE_GESTOR > ROLE_ADVOGADO \n " +
                "ROLE_ADVOGADO > ROLE_ANALISTA \n " +
                "ROLE_ANALISTA > ROLE_ESTAGIARIO";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // ✅ CSRF desabilitado (comum em APIs REST com JWT)
                .csrf(csrf -> csrf.disable())
                
                // ✅ CORS configurado com whitelist de origins
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                
                // ✅ Stateless (sem sessões - usa JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // ✅ Headers de segurança
                .headers(headers -> headers
                    .frameOptions(frame -> frame.deny())  // Previne clickjacking
                    .xssProtection(xss -> xss.disable())  // Browser já faz isso
                    .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))
                )
                
                // ✅ Autorização por rota
                .authorizeHttpRequests(authorize -> authorize
                        // Rotas PÚBLICAS (sem autenticação)
                        .requestMatchers(HttpMethod.POST, "/api/empresas").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // CORS preflight
                        
                        // Rotas de EMPRESA (requer autenticação + role)
                        .requestMatchers(HttpMethod.GET, "/api/empresas/**").hasAnyRole("ADMIN", "GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/empresas/**").hasAnyRole("ADMIN", "GESTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/empresas/**").hasRole("ADMIN")
                        
                        // Rotas de CONTRATOS (ajuste conforme suas regras)
                        .requestMatchers("/api/contratos/**").authenticated()
                        
                        // Rotas de FUNCIONÁRIOS (ajuste conforme suas regras)
                        .requestMatchers("/api/funcionarios/**").hasAnyRole("ADMIN", "GESTOR")
                        
                        // Qualquer outra rota /api exige autenticação
                        .requestMatchers("/api/**").authenticated()
                        
                        // Bloqueia tudo que não foi explicitamente permitido
                        .anyRequest().authenticated()
                )
                
                // ✅ Adiciona filtro JWT ANTES do filtro de autenticação padrão
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                
                build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

*/