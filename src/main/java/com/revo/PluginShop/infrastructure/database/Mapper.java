package com.revo.PluginShop.infrastructure.database;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.dto.VersionDto;

import java.util.List;
import java.util.stream.Collectors;

class Mapper {

    static UserEntity toEntity(UserDto userDto){
        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .locked(userDto.isLocked())
                .enabled(userDto.isEnabled())
                .authority(userDto.getAuthority())
                .plugins(mapPluginsToEntity(userDto.getPlugins()))
                .build();
    }

    private static List<PluginEntity> mapPluginsToEntity(List<PluginDto> plugins) {
        return plugins.stream()
                .map(Mapper::toEntity)
                .collect(Collectors.toList());
    }

    static PluginEntity toEntity(PluginDto pluginDto){
        return PluginEntity.builder()
                .id(pluginDto.getId())
                .userId(pluginDto.getUserId())
                .name(pluginDto.getName())
                .description(pluginDto.getDescription())
                .type(pluginDto.getType())
                .price(pluginDto.getPrice())
                .versions(mapVersionsToEntity(pluginDto.getVersions()))
                .minecraftVersion(pluginDto.getMinecraftVersion())
                .videoUrl(pluginDto.getVideoUrl())
                .icon(pluginDto.getIcon())
                .build();
    }

    private static List<VersionEntity> mapVersionsToEntity(List<VersionDto> versions) {
        return versions.stream()
                .map(Mapper::toEntity)
                .collect(Collectors.toList());
    }

    static VersionEntity toEntity(VersionDto versionDto) {
        return VersionEntity.builder()
                .id(versionDto.getId())
                .pluginId(versionDto.getPluginId())
                .version(versionDto.getVersion())
                .file(versionDto.getFile())
                .changelog(versionDto.getChangelog())
                .build();
    }

    static UserDto toDomain(UserEntity userEntity){
        return UserDto.Builder.anUserDto()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .locked(userEntity.isLocked())
                .enabled(userEntity.isEnabled())
                .authority(userEntity.getAuthority())
                .plugins(mapPluginsToDomain(userEntity.getPlugins()))
                .build();
    }

    private static List<PluginDto> mapPluginsToDomain(List<PluginEntity> plugins) {
        return plugins.stream()
                .map(Mapper::toDomain)
                .collect(Collectors.toList());
    }

    static PluginDto toDomain(PluginEntity pluginEntity){
        return PluginDto.Builder.aPluginDto()
                .id(pluginEntity.getId())
                .userId(pluginEntity.getUserId())
                .name(pluginEntity.getName())
                .description(pluginEntity.getDescription())
                .type(pluginEntity.getType())
                .price(pluginEntity.getPrice())
                .versions(mapVersionsToDomain(pluginEntity.getVersions()))
                .minecraftVersion(pluginEntity.getMinecraftVersion())
                .videoUrl(pluginEntity.getVideoUrl())
                .icon(pluginEntity.getIcon())
                .build();
    }

    private static List<VersionDto> mapVersionsToDomain(List<VersionEntity> versions) {
        return versions.stream()
                .map(Mapper::toDomain)
                .collect(Collectors.toList());
    }

    static VersionDto toDomain(VersionEntity versionEntity) {
        return VersionDto.Builder.aVersionDto()
                .id(versionEntity.getId())
                .pluginId(versionEntity.getPluginId())
                .version(versionEntity.getVersion())
                .file(versionEntity.getFile())
                .changelog(versionEntity.getChangelog())
                .build();
    }
}
