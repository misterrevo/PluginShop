package com.revo.PluginShop.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plugins_versions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class VersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "plugin_id", nullable = false)
    private Long pluginId;
    @Column(nullable = false)
    private String version;
    @Column(nullable = false, unique = true)
    private String file;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String changelog;
}
