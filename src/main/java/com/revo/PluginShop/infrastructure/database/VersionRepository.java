package com.revo.PluginShop.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VersionRepository extends JpaRepository<VersionEntity, Long> {

    boolean existsByFile(String fileName);
}
