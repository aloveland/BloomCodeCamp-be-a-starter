package com.hcc.filters;

import com.hcc.DAO.UserDAO;
import com.hcc.PostAssignmentActivity;
import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.http.HttpHeaders;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class jwtFilter extends OncePerRequestFilter {

    private UserRepository userRepo;

    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(jwtFilter.class);

    @Autowired
    public jwtFilter(UserRepository userRepo, JwtUtil jwtUtil){
            this.userRepo = userRepo;
            this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        jwtUtil = new JwtUtil();
        // Get Authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("HEADER: ---->> {}", header);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }


        // Get Jwt Token
        final String token = header.split(" ")[1].trim();

        // Get user identity
        UserDetails userDetails = userRepo.findByUsername(jwtUtil.getUsernameFromToken(token)).orElse(null);
        logger.info("TOKEN---> {}", token);
        logger.info("userDetails---> {}", userDetails);
        logger.info(String.valueOf(userRepo.findByUsername(jwtUtil.getUsernameFromToken((token)))));
        if (!jwtUtil.validateToken(token, userDetails)) {
            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);

    }
}