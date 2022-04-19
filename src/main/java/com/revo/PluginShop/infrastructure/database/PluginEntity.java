package com.revo.PluginShop.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plugins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class PluginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private double price;
    @OneToMany(cascade = CascadeType.PERSIST)
    @OrderBy("version")
    @JoinColumn(name = "plugin_id")
    private List<VersionEntity> versions = new ArrayList<>();
    @Column(nullable = false)
    private double minecraftVersion;
    @Column(nullable = false)
    private String videoUrl;
    @Column(nullable = false)
    private String icon;
}