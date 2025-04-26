package tn.isi.management.utils.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Allow access to Swagger/OpenAPI documentation
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Public endpoints for authentication
                        .requestMatchers("/auth/**").permitAll()
                        // ADMIN endpoints
                        .requestMatchers(
                            // UserResource
                            "/users/v1/admin/**",
                            // RoleResource
                            "/roles/v1/admin/**",
                            // StructureResource
                            "/structures/v1/admin/**",
                            // ProfileResource
                            "/profiles/v1/admin/**",
                            // DomainResource
                            "/domains/v1/admin/**",
                            // CourseResource
                            "/courses/v1/admin/**"
                        ).hasRole("ADMIN")
                        // MANAGER endpoints (ADMIN and MANAGER)
                        .requestMatchers(
                            // UserResource (none for manager)
                            // RoleResource
                            "/roles/v1/manager/**", "/roles/v1/manager",
                            // StructureResource
                            "/structures/v1/manager/**", "/structures/v1/manager",
                            // ProfileResource
                            "/profiles/v1/manager/**", "/profiles/v1/manager",
                            // DomainResource
                            "/domains/v1/manager/**", "/domains/v1/manager",
                            // CourseResource
                            "/courses/v1/manager/**", "/courses/v1/manager",
                            // EmployerResource
                            "/employers/v1/manager/**", "/employers/v1/manager",
                            // InstructorResource
                            "/instructors/v1/manager/**", "/instructors/v1/manager",
                            // ParticipantResource
                            "/participants/v1/manager/**", "/participants/v1/manager"
                        ).hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated()
                );
        // Using our custom authenticationProvider which uses the UserDetailsService
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Explicitly configure DaoAuthenticationProvider to use our UserDetailsService
     * This ensures proper username/password authentication
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        // Enable detailed error messages for debugging (disable in production)
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
