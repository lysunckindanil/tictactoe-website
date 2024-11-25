package com.game.tictactoe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/").hasRole("USER"))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .formLogin(form -> form.loginPage("/users/login").loginProcessingUrl("/users/login").
                        failureUrl("/users/login?error=true").defaultSuccessUrl("/").permitAll())
                .logout(form -> form.logoutUrl("/users/logout").logoutSuccessUrl("/users/login"))
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }
}
