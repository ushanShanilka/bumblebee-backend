package com.bumblebee.bumblebeebackend.config;

import com.bumblebee.bumblebeebackend.filter.JwtRequestFilter;
import com.bumblebee.bumblebeebackend.filter.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.*;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 2/16/2023
 **/

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailService> configurer = auth.userDetailsService(userDetailService);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/swagger-ui/*",
                        "/v3/api-docs",
                        "/v3/api-docs/swagger-config",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/api/v1/authenticates/admin/login",
                        "/api/v1/authenticates/user/login",
                        "/api/v1/authenticates/user/signup",
                        "/api/v1/otp/**")
                .permitAll()
                .antMatchers().hasAuthority("USER")
                .antMatchers().hasAnyAuthority("ADMIN", "SUPERADMIN", "USER")
                .antMatchers("/api/v1/test").hasAnyAuthority("ADMIN", "SUPERADMIN")
                .antMatchers("/api/v1/authenticate/admin").hasAnyAuthority("SUPERADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().and().csrf().disable();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
