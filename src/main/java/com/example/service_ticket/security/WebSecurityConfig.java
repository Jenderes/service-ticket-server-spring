package com.example.service_ticket.security;

import com.example.service_ticket.controller.APIConstant;
import com.example.service_ticket.security.jwt.JwtAuthEntryPoint;
import com.example.service_ticket.security.jwt.JwtConfigurer;
import com.example.service_ticket.security.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtProvider;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private static final String AUTH_ENDPOINT = APIConstant.API + APIConstant.AUTH + APIConstant.ANY;
    private static final String[] TROUBLE_TICKET_ENDPOINT = {
            APIConstant.API + APIConstant.TICKET,
            APIConstant.API + APIConstant.TICKET + APIConstant.ANY,
    };
    private static final String DICTIONARY_ENDPOINT = APIConstant.API + APIConstant.DICTIONARY + APIConstant.ANY_ANY;
    private static final String[] SWAGGER_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
    public WebSecurityConfig(JwtProvider jwtProvider, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtProvider = jwtProvider;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(this.jwtAuthEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT).permitAll()
                .antMatchers(TROUBLE_TICKET_ENDPOINT).hasRole("USER")
                .antMatchers(DICTIONARY_ENDPOINT).hasRole("USER")
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .anyRequest().authenticated();

        httpSecurity.apply(new JwtConfigurer(jwtProvider));
    }
}
