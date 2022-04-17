package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.dto.UserDto;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<UserDto> getUserByEmail(String email);
    boolean existsUserByEmail(String email);
    UserDto saveUser(UserDto userDto);
}
