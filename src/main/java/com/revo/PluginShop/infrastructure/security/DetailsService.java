package com.revo.PluginShop.infrastructure.security;

import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
class DetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDto = getUser(username);
        return new Details(userDto);
    }

    private UserDto getUser(String username) {
        return userRepositoryPort.getUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
