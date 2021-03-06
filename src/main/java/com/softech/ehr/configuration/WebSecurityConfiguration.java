package com.softech.ehr.configuration;

import com.softech.ehr.security.AuthenticationTokenFilter;
import com.softech.ehr.security.EntryPointUnauthorizedHandler;
import com.softech.ehr.security.TokenUtils;
import com.softech.ehr.service.ISecurityService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final EntryPointUnauthorizedHandler unauthorizedHandler;
    private final UserDetailsService userDetailsService;
    private final ISecurityService ISecurityService;
    private final TokenUtils tokenUtils;

    public WebSecurityConfiguration(
        EntryPointUnauthorizedHandler unauthorizedHandler,
        UserDetailsService userDetailsService, ISecurityService ISecurityService,
        TokenUtils tokenUtils) {

        this.unauthorizedHandler = unauthorizedHandler;
        this.userDetailsService = userDetailsService;
        this.ISecurityService = ISecurityService;
        this.tokenUtils = tokenUtils;
    }

    public void configureAuthentication(
        AuthenticationManagerBuilder authenticationManagerBuilder)
        throws Exception {
        authenticationManagerBuilder
            .userDetailsService(this.userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean()
        throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter =
            new AuthenticationTokenFilter(tokenUtils, userDetailsService);
        authenticationTokenFilter.setAuthenticationManager(
            authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Bean
    public ISecurityService securityService() {
        return this.ISecurityService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .exceptionHandling()
            .authenticationEntryPoint(this.unauthorizedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(
                "/auth/**",
                "/h2-console/**",
                "/api-docs/**",
                "/swagger-ui/**",
                "/v1/users/test",
                "/actuator/**",
                "/v1/auth/login",
                "/v1/auth/refresh",
                "/bus/v3/api-docs/**",
                "/swagger-ui.html",
                "/swagger-ui/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .cors()
            .and()
            .csrf()
            .disable();


        // Custom JWT based authentication
        httpSecurity
            .addFilterBefore(authenticationTokenFilterBean(),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
        // ignore swagger
        web.ignoring().mvcMatchers(
            "/swagger-ui.html/**",
            "/configuration/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v1/auth/login",
            "/v3/api-docs",
            "/bus/v3/api-docs/**",
            "/webjars/**");
    }


}
