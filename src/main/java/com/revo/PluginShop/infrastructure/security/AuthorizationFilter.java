package com.revo.PluginShop.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
class AuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String EMPTY_STRING = "";
    private final DetailsService detailsService;
    @Value("${spring.security.jwt.secret}")
    private String secret;

    public AuthorizationFilter(AuthenticationManager authenticationManager, DetailsService detailsService) {
        super(authenticationManager);
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        var authentication = getAuthenticationToken(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        var token = request.getHeader(TOKEN_HEADER);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            var userName = getName(token);
            if (userName != null) {
                UserDetails userDetails = getDetails(userName);
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
                        userDetails.getAuthorities());
            }
        }
        return null;
    }

    private UserDetails getDetails(String userName){
        return detailsService.loadUserByUsername(userName);
    }

    private String getName(String token){
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX, EMPTY_STRING))
                .getSubject();
    }
}
