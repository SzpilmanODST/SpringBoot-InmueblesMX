package com.InmueblesMX.config;

import com.InmueblesMX.config.filter.JwtTokenValidator;
import com.InmueblesMX.service.user.UserDetailsServiceImpl;
import com.InmueblesMX.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // Public endpoints
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/properties/findAll").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/properties/find/{id}").permitAll();

                    // Private endpoints
                    http.requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("ADMIN", "BUYER", "SELLER");
                    http.requestMatchers(HttpMethod.PUT, "/users/update/{id}").hasAnyRole("ADMIN", "BUYER", "SELLER");
                    http.requestMatchers(HttpMethod.DELETE, "/users/delete/{id}").hasAnyRole("ADMIN", "BUYER", "SELLER");

                    http.requestMatchers(HttpMethod.GET, "/properties/find/{id}/auth").hasAnyRole("ADMIN", "BUYER", "SELLER");
                    http.requestMatchers(HttpMethod.POST, "/properties/save").hasAnyRole("SELLER");
                    http.requestMatchers(HttpMethod.PUT, "/properties/update/{id}").hasAnyRole("SELLER");
                    http.requestMatchers(HttpMethod.DELETE, "/properties/delete/{id}").hasAnyRole("SELLER", "ADMIN");

                    http.requestMatchers(HttpMethod.GET, "/photos/find{id}").hasAnyRole("ADMIN", "SELLER");
                    http.requestMatchers(HttpMethod.GET, "/photos/view/{photoName}").hasAnyRole("ADMIN", "SELLER");
                    http.requestMatchers(HttpMethod.DELETE, "photos/delete/{id}").hasAnyRole("ADMIN", "SELLER");
                    http.requestMatchers(HttpMethod.POST, "photos/save").hasAnyRole("SELLER");

                    // Not specified endpoints
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
