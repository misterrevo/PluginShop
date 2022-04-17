package com.revo.PluginShop.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface PluginRepository extends JpaRepository<PluginEntity, Long> {
    List<PluginEntity> findAllByType(String type);
    List<PluginEntity> findAllByNameContaining(String name);
    boolean existsByIcon(String icon);
    @Query(value = "SELECT max(p.id) FROM PluginEntity p")
    long getNextIdOfPlugin();
}
