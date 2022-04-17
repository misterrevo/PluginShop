package com.revo.PluginShop.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByEmail(String email);
    boolean existsUserByEmail(String email);
}
