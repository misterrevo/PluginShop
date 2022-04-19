package com.revo.PluginShop.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByEmail(String email);
    boolean existsUserByEmail(String email);
}
