package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.UserRestDto;

public interface UserServicePort {

    UserDto createNewUser(UserRestDto createDto);
    UserDto getUserByEmail(String email);
    UserDto getUserByToken(String token);
    UserDto changeBlockStatus(String email, boolean status);
}
