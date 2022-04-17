package com.revo.PluginShop.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;

interface VersionRepository extends JpaRepository<VersionEntity, Long> {

    boolean existsByFile(String fileName);
}
