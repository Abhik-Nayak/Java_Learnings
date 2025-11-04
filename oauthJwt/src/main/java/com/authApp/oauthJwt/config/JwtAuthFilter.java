package com.authApp.oauthJwt.config;

import com.authApp.oauthJwt.service.CustomUserDetailsService;
import com.authApp.oauthJwt.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

//1️⃣ Class Definition & Imports
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    //OncePerRequestFilter => ensures the filter runs once per request, not multiple times per chain.

//  2️⃣ Dependencies
    private final JwtService jwtService; //Handle token parsing and validation
    private final CustomUserDetailsService userDetailsService; //Loads user details (roles,password) from DB.

//  3️⃣ Constructor Injection
    public JwtAuthFilter(JwtService jwtService, CustomUserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        log.info("JwtAuthFilter initialized successfully.");
    }
    //Constructor-based dependency injection (Clean and testable)

//  4️⃣ Overridden Method (This is the core method  - it's automatically invoked by Spring security for ever HTTP request.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        //Fetched the Authorization header from the incoming HTTP request
        final String header = request.getHeader("Authorization");
        final String token;
        final String username;

        log.info("Processing request URI: {}", request.getRequestURI());

        // 1️⃣ Check if header contains Bearer token
        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("No JWT token found in Authorization header for request: {}", request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        // 2️⃣ Extract token from header (skip "Bearer ")
        token = header.substring(7);
        log.debug("Extracted JWT token: {}", token);

        // 3️⃣ Extract username (subject) from token
        username = jwtService.extractUsername(token);
        log.info("Extracted username from token: {}", username);

        // 4️⃣ Authenticate only if user not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.debug("Loading user details for username: {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 5️⃣ Validate token
            if (jwtService.validateToken(token)) {
                log.debug("Loading user details for username: {}", username);

                // 6️⃣ Create authentication token and set it in SecurityContext
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Security context updated with authenticated user: {}", username);
            } else {
                log.warn("Invalid JWT token for user: {}", username);
            }
        } else {
            log.debug("User already authenticated or username is null — skipping authentication setup.");
        }

        // 7️⃣ Continue filter chain
        chain.doFilter(request, response);
        log.debug("Completed JWT filter for request URI: {}", request.getRequestURI());
    }
}