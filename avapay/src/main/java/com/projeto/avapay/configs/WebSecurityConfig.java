package com.projeto.avapay.configs;
 
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.projeto.avapay.service.CustomService;

import jakarta.servlet.http.HttpServletResponse;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
 
    @Autowired
    private CustomService userDetailsService;
 
    // Bean para codificador de senha (bcrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    // Configuração do AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder()); // Usa o método passwordEncoder() para obter o bean
        return authenticationManagerBuilder.build();
    }
 
    // Configuração de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
 
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080"));
        config.setAllowedMethods(Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.OPTIONS.name()
        ));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", 
        		"Access-Control-Allow-Headers", "*"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
 
        return source;
    }
 
    // Configuração da cadeia de filtros de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                
                
               //-------------------------client----------------------------------------//
                                
                .requestMatchers(HttpMethod.GET, "/api/clients/**").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/api/clients/**").permitAll()
                
                .requestMatchers(HttpMethod.PUT, "/api/clients/**").permitAll()
                
                .requestMatchers(HttpMethod.DELETE, "/api/clients/**").permitAll()
                
                //---------------------------address-----------------------------------------//
                .requestMatchers(HttpMethod.GET, "/api/account/**").permitAll() 
                		
                
                .requestMatchers(HttpMethod.POST, "/api/account/**").permitAll()
                		
                
                .requestMatchers(HttpMethod.PUT, "/api/account/**").permitAll()
                
                .requestMatchers(HttpMethod.DELETE, "/api/account/**").permitAll()
                //--------------------------------------address-------------------------------//
                .requestMatchers(HttpMethod.GET, "/api/addresses/**").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/api/addresses/**").permitAll()
                
                .requestMatchers(HttpMethod.PUT, "/api/addresses/**").permitAll()
                
                .requestMatchers(HttpMethod.DELETE, "/api/addresses/**").permitAll()
                
                //--------------------------------------account-------------------------------//
                .requestMatchers(HttpMethod.GET, "/api/cadastroPix/**").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/api/cadastroPix/**").permitAll()
                
                .requestMatchers(HttpMethod.PUT, "/api/cadastroPix/**").permitAll()
                
                .requestMatchers(HttpMethod.DELETE, "/api/cadastroPix/**").permitAll()
              //--------------------------------------account-------------------------------//
                .requestMatchers(HttpMethod.GET, "/api/transactions/**").permitAll()
                
                .requestMatchers(HttpMethod.POST, "/api/transactions/**").permitAll()
                
                .requestMatchers(HttpMethod.PUT, "/api/transactions/**").permitAll()
                
                .requestMatchers(HttpMethod.DELETE, "/api/transactions/**").permitAll()
             
                 // Permitir acesso para usuários autenticados
 
                .anyRequest().authenticated()
            )
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new org.springframework.security.web.util.matcher
                		.AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .httpBasic(httpBasic -> httpBasic
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                })
            )
            .csrf(csrf -> csrf.disable());
 
        return http.build();
    }
}