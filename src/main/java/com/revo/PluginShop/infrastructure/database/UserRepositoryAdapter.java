package com.revo.PluginShop.infrastructure.database;

import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    @Override
    public Optional<UserDto> getUserByEmail(String email) {
        var user = userRepository.findUserByEmail(email);
        return user.map(Mapper::toDomain);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        var user = Mapper.toEntity(userDto);
        var savedUser = userRepository.save(user);
        return Mapper.toDomain(savedUser);
    }
}
