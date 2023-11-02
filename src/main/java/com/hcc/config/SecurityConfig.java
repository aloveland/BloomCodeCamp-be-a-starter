package com.hcc.config;

import com.hcc.filters.jwtFilter;
import com.hcc.services.UserDetailServiceImpl;
import com.hcc.utils.CustomPasswordEncoder;
import com.hcc.utils.JwtAuthenticationFilter;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    CustomPasswordEncoder customPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(customPasswordEncoder.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http
                .authorizeRequests()
                .antMatchers("/createUser", "/welcome").permitAll()  // permit access to these endpoints
                .anyRequest().authenticated()  // all other endpoints require authentication
                .and()
                .formLogin()
                .loginPage("/login")  // specify your login page
                .permitAll();

         */



        super.configure(http);
    }

}