package com.mercadolivre.apimlv2.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UsersService usersService;
    private final TokenManager tokenManager;

    public SecurityConfiguration(UsersService usersService, TokenManager tokenManager) {
        this.usersService = usersService;
        this.tokenManager = tokenManager;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.usersService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/products/{id:[0-9]+}").permitAll()
                    .antMatchers(HttpMethod.POST, "/users").permitAll()
                    .antMatchers("/nota-fiscal").permitAll()
                    .antMatchers("/sistema-ranking").permitAll()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/swagger-resources/**",
                                 "/swagger-ui.html",
                                 "/v2/api-docs",
                                 "/webjars/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .cors()
                .and()
                    .csrf().disable()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(tokenManager, usersService), UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
    }

    private static class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

        private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            logger.error("Access not verified was checked. Message: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized to use this resource");
        }
    }
}
