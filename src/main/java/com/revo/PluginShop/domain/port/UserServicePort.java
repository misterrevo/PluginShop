package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.UserCredentialsDto;

public interface UserServicePort {

    UserDto createNewUser(UserCredentialsDto createDto);
    UserDto getUserByEmail(String email);
    UserDto changeBlockStatus(String email, boolean status);
}
