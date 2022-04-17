package com.revo.PluginShop.infrastructure.security;

import com.revo.PluginShop.domain.port.EncoderPort;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
class AuthManager implements AuthenticationManager {

    private static final String NULL_EXCEPTION_MESSAGE = "Credentials can't be null!";
    private static final String PASSWORD_EXCEPTION_MESSAGE = "Credentials are wrong!";
    private static final String LOCKED_EXCEPTION_MESSAGE = "Account is locked!";

    private final DetailsService detailsService;
    private final EncoderPort encoderPort;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var userDetails = loadUser(authentication);
        if(authentication.getPrincipal() == null || authentication.getCredentials() == null){
            throw new BadCredentialsException(NULL_EXCEPTION_MESSAGE);
        }
        var credentials = authentication.getCredentials();
        if(!encoderPort.matches(credentials.toString(), userDetails.getPassword())){
            throw new BadCredentialsException(PASSWORD_EXCEPTION_MESSAGE);
        }
        if(!userDetails.isAccountNonLocked()){
            throw new BadCredentialsException(LOCKED_EXCEPTION_MESSAGE);
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }

    private UserDetails loadUser(Authentication authentication) {
        var principal = authentication.getPrincipal();
        return detailsService.loadUserByUsername(principal.toString());
    }
}
