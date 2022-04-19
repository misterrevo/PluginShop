package com.revo.PluginShop.infrastructure.database;

import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.revo.PluginShop.infrastructure.database.Mapper.toDomain;
import static com.revo.PluginShop.infrastructure.database.Mapper.toEntity;

@RequiredArgsConstructor
@Component
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
        var user = toEntity(userDto);
        var savedUser = userRepository.save(user);
        return toDomain(savedUser);
    }
}
