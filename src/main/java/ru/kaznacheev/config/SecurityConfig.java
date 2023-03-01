package ru.kaznacheev.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private SecretKeyAuthenticationFilter secretKeyAuthenticationFilter;
    private HandleCustomExceptionFilter handleCustomExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(secretKeyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(handleCustomExceptionFilter, ChannelProcessingFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/clients/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/rates").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/rates").hasRole("ADMIN")
                .antMatchers("/api/v1/stats/**").hasRole("ADMIN")
                .antMatchers("/api/v1/wallets/**").hasRole("USER");
        return httpSecurity.build();
    }

}
