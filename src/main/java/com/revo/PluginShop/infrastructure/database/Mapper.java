package com.revo.PluginShop.infrastructure.database;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.dto.VersionDto;

import java.util.List;

class Mapper {

    static UserEntity toEntity(UserDto userDto){
        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .locked(userDto.isLocked())
                .enabled(userDto.isEnabled())
                .plugins(mapPluginsToEntity(userDto.getPlugins()))
                .build();
    }

    private static List<PluginEntity> mapPluginsToEntity(List<PluginDto> plugins) {
        return null;
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
                .videoUrl(pluginDto.getVideoUrl())
                .icon(pluginDto.getIcon())
                .build();
    }

    private static List<VersionEntity> mapVersionsToEntity(List<VersionDto> versions) {
        return versions.stream()
                .map(Mapper::toEntity)
                .toList();
    }

    private static VersionEntity toEntity(VersionDto versionDto) {
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
                .plugins(mapPluginsToDomain(userEntity.getPlugins()))
                .build();
    }

    private static List<PluginDto> mapPluginsToDomain(List<PluginEntity> plugins) {
        return plugins.stream()
                .map(Mapper::toDomain)
                .toList();
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
                .videoUrl(pluginEntity.getVideoUrl())
                .icon(pluginEntity.getIcon())
                .build();
    }

    private static List<VersionDto> mapVersionsToDomain(List<VersionEntity> versions) {
        return versions.stream()
                .map(Mapper::toDomain)
                .toList();
    }

    private static VersionDto toDomain(VersionEntity versionEntity) {
        return VersionDto.Builder.aVersionDto()
                .id(versionEntity.getId())
                .pluginId(versionEntity.getPluginId())
                .version(versionEntity.getVersion())
                .file(versionEntity.getFile())
                .changelog(versionEntity.getChangelog())
                .build();
    }
}
