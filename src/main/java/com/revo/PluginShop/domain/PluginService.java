package com.revo.PluginShop.domain;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.exception.FileSavingException;
import com.revo.PluginShop.domain.exception.PluginDoesNotExistsException;
import com.revo.PluginShop.domain.exception.UserDoesNotExistsException;
import com.revo.PluginShop.domain.port.JwtPort;
import com.revo.PluginShop.domain.port.PluginRepositoryPort;
import com.revo.PluginShop.domain.port.PluginServicePort;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import com.revo.PluginShop.domain.port.UserServicePort;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginCreateDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginEditDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PluginService implements PluginServicePort {

    private static final String ICON_RESOURCE_PATH = "src/main/resources/icons";
    private static final String PLUGIN_RESOURCE_PATH = "src/main/resources/plugins";
    private static final String ICON_FORMAT = ".png";
    private static final String PLUGIN_FORMAT = ".jar";
    private static final String FIRST_VERSION_NAME = "0.0.1";
    private static final String FIRST_VERSION_CHANGELOG = "IT'S FIRST VERSION OF PLUGIN!";

    private final PluginRepositoryPort pluginRepositoryPort;
    private final JwtPort jwtPort;
    private final UserRepositoryPort userRepositoryPort;

    public PluginService(PluginRepositoryPort pluginRepositoryPort, JwtPort jwtPort, UserRepositoryPort userRepositoryPort) {
        this.pluginRepositoryPort = pluginRepositoryPort;
        this.jwtPort = jwtPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public List<PluginDto> getAllFreePlugins() {
        return pluginRepositoryPort.getAllFreePlugins();
    }

    @Override
    public List<PluginDto> getAllByNameContaining(String name) {
        return pluginRepositoryPort.getAllContainsName(name);
    }

    @Override
    public List<PluginDto> getAllPremiumPlugins() {
        return pluginRepositoryPort.getAllPremiumPlugins();
    }

    @Override
    public PluginDto createNewPlugin(PluginCreateDto createDto, MultipartFile pluginFile, MultipartFile iconFile) {
        var pluginFileName = savePlugin(pluginFile);
        var iconFileName = saveIcon(iconFile);
        var plugin = buildPlugin(createDto, pluginFileName, iconFileName);
        var pluginDto = Mapper.toDto(plugin);
        return save(pluginDto);
    }

    private PluginDto save(PluginDto pluginDto) {
        return pluginRepositoryPort.savePlugin(pluginDto);
    }

    private Plugin buildPlugin(PluginCreateDto createDto, String pluginFileName, String iconFileName) {
        return Plugin.Builder.aPlugin()
                .name(createDto.getName())
                .description(createDto.getDescription())
                .type(PluginType.valueOf(createDto.getType()))
                .price(createDto.getPrice())
                .versions(Arrays.asList(
                        Version.Builder.aVersion()
                                .version(FIRST_VERSION_NAME)
                                .file(pluginFileName)
                                .changelog(FIRST_VERSION_CHANGELOG)
                                .pluginId(getNextPluginId())
                                .build()
                ))
                .videoUrl(createDto.getVideoUrl())
                .icon(iconFileName)
                .build();
    }

    private Long getNextPluginId() {
        return pluginRepositoryPort.getNextIdOfPlugin();
    }

    private String saveIcon(MultipartFile iconFile) {
        var name = generateName();
        while(existsIconByName(name)){
            name = generateName();
        }
        try {
            var file = new File(ICON_RESOURCE_PATH +name+ICON_FORMAT);
            file.createNewFile();
            iconFile.transferTo(file);
        } catch (Exception exception){
            throw new FileSavingException();
        }
        return name;
    }

    private boolean existsIconByName(String name) {
        return pluginRepositoryPort.existsIconByName(name);
    }

    private boolean existsFileByName(String name) {
        return pluginRepositoryPort.existsFileByName(name);
    }

    private String generateName() {
        return UUID.randomUUID().toString();
    }

    private String savePlugin(MultipartFile pluginFile) {
        var name = generateName();
        while(existsFileByName(name)){
            name = generateName();
        }
        try {
            var file = new File(PLUGIN_RESOURCE_PATH +name+PLUGIN_FORMAT);
            file.createNewFile();
            pluginFile.transferTo(file);
        } catch (Exception exception){
            throw new FileSavingException();
        }
        return name;
    }

    @Override
    public PluginDto changePluginData(PluginEditDto editDto) {
        var plugin = getPlugin(editDto.getId());
        plugin.setDescription(editDto.getDescription());
        plugin.setPrice(editDto.getPrice());
        plugin.setName(editDto.getName());
        plugin.setType(PluginType.valueOf(editDto.getType()));
        plugin.setVideoUrl(editDto.getVideoUrl());
        var pluginDto = Mapper.toDto(plugin);
        return save(pluginDto);
    }

    private Plugin getPlugin(Long id) {
        var pluginDto = pluginRepositoryPort.getPluginById(id);
        var plugin = pluginDto.map(Mapper::fromDto);
        return plugin.orElseThrow(() -> new PluginDoesNotExistsException(id));
    }

    @Override
    public PluginDto changePluginIcon(Long id, MultipartFile iconFile) {
        var plugin = getPlugin(id);
        var iconFileName = saveIcon(iconFile);
        plugin.setIcon(iconFileName);
        var pluginDto = Mapper.toDto(plugin);
        return save(pluginDto);
    }

    @Override
    public PluginDto updatePluginFile(Long id, MultipartFile pluginFile) {
        var plugin = getPlugin(id);
        var pluginFileName = savePlugin(pluginFile);
        plugin.setIcon(pluginFileName);
        var pluginDto = Mapper.toDto(plugin);
        return save(pluginDto);
    }

    @Override
    public PluginDto buyPlugin(Long id, String token) {
        var email = jwtPort.getEmailFromToken(token);
        var userDto = getUser(email);
        var user = Mapper.fromDto(userDto);
        var plugins = user.getPlugins();
        var plugin = getPlugin(id);
        plugins.add(plugin);
        var updatedUserDto = Mapper.toDto(user);
        userRepositoryPort.saveUser(updatedUserDto);
        return Mapper.toDto(plugin);
    }

    private UserDto getUser(String email) {
        return userRepositoryPort.getUserByEmail(email)
                .orElseThrow(() -> new UserDoesNotExistsException(email));
    }
}
