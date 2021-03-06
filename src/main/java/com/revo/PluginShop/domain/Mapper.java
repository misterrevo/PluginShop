package com.revo.PluginShop.domain;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.dto.VersionDto;

import java.util.List;
import java.util.stream.Collectors;

class Mapper {

    static User fromDto(UserDto userDto){
        return User.Builder.anUser()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .locked(userDto.isLocked())
                .enabled(userDto.isEnabled())
                .authority(userDto.getAuthority())
                .plugins(mapPluginsFromDto(userDto.getPlugins()))
                .build();
    }

    private static List<Plugin> mapPluginsFromDto(List<PluginDto> plugins) {
        return plugins.stream()
                .map(Mapper::fromDto)
                .collect(Collectors.toList());
    }

    static Plugin fromDto(PluginDto pluginDto){
        return Plugin.Builder.aPlugin()
                .id(pluginDto.getId())
                .name(pluginDto.getName())
                .description(pluginDto.getDescription())
                .type(PluginType.valueOf(pluginDto.getType()))
                .price(pluginDto.getPrice())
                .versions(mapVersionsFromDto(pluginDto.getVersions(), pluginDto.getId()))
                .minecraftVersion(pluginDto.getMinecraftVersion())
                .videoUrl(pluginDto.getVideoUrl())
                .icon(pluginDto.getIcon())
                .build();
    }

    private static List<Version> mapVersionsFromDto(List<VersionDto> versions, Long id) {
        return versions.stream()
                .map(target -> mapVersionFromDto(target, id))
                .collect(Collectors.toList());
    }

    private static Version mapVersionFromDto(VersionDto target, Long id) {
        return Version.Builder.aVersion()
                .id(target.getId())
                .changelog(target.getChangelog())
                .file(target.getFile())
                .pluginId(target.getPluginId())
                .version(target.getVersion())
                .build();
    }

    static UserDto toDto(User user){
        return UserDto.Builder.anUserDto()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .locked(user.isLocked())
                .enabled(user.isEnabled())
                .authority(user.getAuthority())
                .plugins(mapPluginsToDto(user.getPlugins(), user.getId()))
                .build();
    }

    private static List<PluginDto> mapPluginsToDto(List<Plugin> plugins, Long userId) {
        return plugins.stream()
                .map(Mapper::toDto)
                .collect(Collectors.toList());
    }

    static PluginDto toDto(Plugin plugin){
        var type = plugin.getType();
        return PluginDto.Builder.aPluginDto()
                .id(plugin.getId())
                .name(plugin.getName())
                .description(plugin.getDescription())
                .type(type.toString())
                .price(plugin.getPrice())
                .versions(mapVersionsToDto(plugin.getVersions(), plugin.getId()))
                .minecraftVersion(plugin.getMinecraftVersion())
                .videoUrl(plugin.getVideoUrl())
                .icon(plugin.getIcon())
                .build();
    }

    private static List<VersionDto> mapVersionsToDto(List<Version> versions, Long id) {
        return versions.stream()
                .map(target -> mapVersionFromListToDto(target, id))
                .collect(Collectors.toList());
    }

    private static VersionDto mapVersionFromListToDto(Version target, Long id) {
        return VersionDto.Builder.aVersionDto()
                .id(target.getId())
                .pluginId(id)
                .version(target.getVersion())
                .file(target.getFile())
                .changelog(target.getChangelog())
                .build();
    }

    static VersionDto toDto(Version version){
        return VersionDto.Builder.aVersionDto()
                .id(version.getId())
                .pluginId(version.getPluginId())
                .version(version.getVersion())
                .file(version.getFile())
                .changelog(version.getChangelog())
                .build();
    }
}
