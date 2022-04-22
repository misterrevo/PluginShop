package com.revo.PluginShop.infrastructure.security;

import com.revo.PluginShop.domain.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Stream;

class Details implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserDto userDto;

    Details(UserDto userDto){
        this.userDto = userDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(userDto.getAuthority())
                .map(target -> new SimpleGrantedAuthority(ROLE_PREFIX+target))
                .toList();
    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userDto.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userDto.isEnabled();
    }
}
