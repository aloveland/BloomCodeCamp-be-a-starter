package com.hcc.utils;

import com.hcc.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    /*
    private final com.hcc.utils.JwtUtil jwtUtil;

    public JwtAuthenticationFilter(com.hcc.utils.JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {
        String token = jwtUtil.generateToken((User) auth.getPrincipal());
        response.addHeader("Authorization", "Bearer " + token);
    }

     */
}
