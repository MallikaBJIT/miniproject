package com.example.miniproject.config;

import com.example.miniproject.auth.JwtAuthenticationFilter;
import com.example.miniproject.entity.User;
import com.example.miniproject.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Qualifier("customAuthenticationEntryPoint")
    private AuthenticationEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "books/create").hasRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.PUT, "books/update/{id}").hasRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "books/delete/{id}").hasRole(AppConstant.ADMIN)
                        .requestMatchers(HttpMethod.GET, "books/all").hasAnyRole(AppConstant.ADMIN, AppConstant.USER)

                        .requestMatchers(HttpMethod.POST, "books/{bookId}/user/{userId}/borrow").hasRole(AppConstant.USER)
                        .requestMatchers(HttpMethod.GET, "books//{bookId}/return").hasRole(AppConstant.USER)
                        //.requestMatchers(HttpMethod.GET, "books/all").hasRole(AppConstant.USER)
                        //.requestMatchers(HttpMethod.GET, "books/all").hasRole(AppConstant.USER)
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(excep -> excep.authenticationEntryPoint(authEntryPoint));

        return http.build();
    }
}
