package com.revo.PluginShop.domain;

import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.exception.UserDoesNotExistsException;
import com.revo.PluginShop.domain.exception.UserNameInUseException;
import com.revo.PluginShop.domain.port.EncoderPort;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import com.revo.PluginShop.domain.port.UserServicePort;
import com.revo.PluginShop.infrastructure.application.rest.dto.UserRestDto;

public class UserService implements UserServicePort {

    private final UserRepositoryPort userRepositoryPort;
    private final EncoderPort encoderPort;

    public UserService(UserRepositoryPort userRepositoryPort, EncoderPort encoderPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.encoderPort = encoderPort;
    }

    @Override
    public UserDto createNewUser(UserRestDto createDto) {
        var email = createDto.getEmail();
        if(existsByEmail(email)){
            throw new UserNameInUseException(email);
        }
        var user = buildUser(createDto);
        var userDto = Mapper.toDto(user);
        return saveUser(userDto);
    }

    private UserDto saveUser(UserDto userDto) {
        return userRepositoryPort.saveUser(userDto);
    }

    private boolean existsByEmail(String email) {
        return userRepositoryPort.existsUserByEmail(email);
    }

    private User buildUser(UserRestDto createDto) {
        var encodedPassword = encodePassword(createDto.getPassword());
        return User.Builder.anUser()
                .email(createDto.getEmail())
                .password(encodedPassword)
                .build();
    }

    private String encodePassword(String password) {
        return encoderPort.encode(password);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepositoryPort.getUserByEmail(email)
                .orElseThrow(() -> new UserDoesNotExistsException(email));
    }

    @Override
    public UserDto changeBlockStatus(String email, boolean status) {
        var userDto = getUserByEmail(email);
        userDto.setEnabled(true);
        return saveUser(userDto);
    }
}
