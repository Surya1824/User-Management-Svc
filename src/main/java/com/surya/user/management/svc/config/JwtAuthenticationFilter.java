package com.surya.user.management.svc.config;

import com.surya.user.management.svc.service.JWTService;
import com.surya.user.management.svc.service.SecureUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter  {

    public final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JWTService jwtService;

    private final SecureUserDetailService secureUserDetailService;

    public JwtAuthenticationFilter(JWTService jwtService, SecureUserDetailService secureUserDetailService) {
        this.jwtService = jwtService;
        this.secureUserDetailService = secureUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.equals("/customer/login") || path.equals("/customer/register")
        || path.equals("/admin/login") || path.equals("/admin/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String bearerJwtToke = request.getHeader("Authorization");

        if(bearerJwtToke == null || !bearerJwtToke.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        final String jwtToken = bearerJwtToke.substring(7);
        final String userName = jwtService.extractUserName(jwtToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(userName != null && authentication == null){
            UserDetails userDetails = secureUserDetailService.loadUserByUsername(userName);

            if(jwtService.isTokenValid(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //for session
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request,response);

    }
}
