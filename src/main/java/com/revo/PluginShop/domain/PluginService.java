package com.revo.PluginShop.domain;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.dto.VersionDto;
import com.revo.PluginShop.domain.exception.FileReadingException;
import com.revo.PluginShop.domain.exception.FileSavingException;
import com.revo.PluginShop.domain.exception.PluginAddException;
import com.revo.PluginShop.domain.exception.PluginDoesNotExistsException;
import com.revo.PluginShop.domain.exception.UserDoesNotExistsException;
import com.revo.PluginShop.domain.exception.UserDoesNotHavePlugin;
import com.revo.PluginShop.domain.port.JwtPort;
import com.revo.PluginShop.domain.port.PluginRepositoryPort;
import com.revo.PluginShop.domain.port.PluginServicePort;
import com.revo.PluginShop.domain.port.UserRepositoryPort;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginRestDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.VersionRestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.revo.PluginShop.domain.Mapper.fromDto;
import static com.revo.PluginShop.domain.Mapper.toDto;

public class PluginService implements PluginServicePort {

    private static final String ICON_RESOURCE_PATH = "src/main/resources/icons/";
    private static final String PLUGIN_RESOURCE_PATH = "src/main/resources/plugins/";
    private static final String ICON_FORMAT = ".png";
    private static final String PLUGIN_FORMAT = ".jar";
    private static final String ICON_CLASSPATH = "/icons/";
    private static final String PLUGIN_CLASSPATH = "/plugins/";

    private final PluginRepositoryPort pluginRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final JwtPort jwtPort;

    public PluginService(PluginRepositoryPort pluginRepositoryPort, UserRepositoryPort userRepositoryPort, JwtPort jwtPort) {
        this.pluginRepositoryPort = pluginRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.jwtPort = jwtPort;
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
    public PluginDto createNewPlugin(PluginRestDto createDto) {
        var plugin = buildPlugin(createDto);
        var pluginDto = toDto(plugin);
        return save(pluginDto);
    }

    private PluginDto save(PluginDto pluginDto) {
        return pluginRepositoryPort.savePlugin(pluginDto);
    }

    private Plugin buildPlugin(PluginRestDto createDto) {
        return Plugin.Builder.aPlugin()
                .name(createDto.getName())
                .description(createDto.getDescription())
                .type(PluginType.valueOf(createDto.getType()))
                .price(createDto.getPrice())
                .videoUrl(createDto.getVideoUrl())
                .minecraftVersion(createDto.getMinecraftVersion())
                .build();
    }

    private String saveIcon(MultipartFile iconFile) {
        var name = generateName();
        while(existsIconByName(name)){
            name = generateName();
        }
        try {
            var file = new File(ICON_RESOURCE_PATH +name+ICON_FORMAT);
            file.createNewFile();
            var outputStream = new FileOutputStream(file);
            outputStream.write(iconFile.getBytes());
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

    private String savePluginFile(MultipartFile pluginFile) {
        var name = generateName();
        while(existsFileByName(name)){
            name = generateName();
        }
        try {
            var file = new File(PLUGIN_RESOURCE_PATH +name+PLUGIN_FORMAT);
            file.createNewFile();
            var outputStream = new FileOutputStream(file);
            outputStream.write(pluginFile.getBytes());
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new FileSavingException();
        }
        return name;
    }

    @Override
    public PluginDto changePluginData(Long id, PluginRestDto editDto) {
        var plugin = getPlugin(id);
        plugin.setDescription(editDto.getDescription());
        plugin.setPrice(editDto.getPrice());
        plugin.setName(editDto.getName());
        plugin.setType(PluginType.valueOf(editDto.getType()));
        plugin.setVideoUrl(editDto.getVideoUrl());
        var pluginDto = toDto(plugin);
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
        var pluginDto = toDto(plugin);
        return save(pluginDto);
    }

    @Override
    public VersionDto updateVersionFile(Long id, MultipartFile pluginFile) {
        var version = getVersionById(id);
        var pluginFileName = savePluginFile(pluginFile);
        version.setFile(pluginFileName);
        return save(version);
    }

    private VersionDto save(VersionDto version) {
        return pluginRepositoryPort.saveVersion(version);
    }

    @Override
    public PluginDto buyPlugin(Long id, String email) {
        var userDto = getUser(email);
        var user = fromDto(userDto);
        var plugins = user.getPlugins();
        var plugin = getPlugin(id);
        add(plugins, plugin);
        var updatedUserDto = toDto(user);
        save(updatedUserDto);
        return toDto(plugin);
    }

    private UserDto save(UserDto updatedUserDto) {
        return userRepositoryPort.saveUser(updatedUserDto);
    }

    private void add(List<Plugin> plugins, Plugin plugin){
        if(!plugins.contains(plugin)){
            plugins.add(plugin);
            return;
        }
        throw new PluginAddException(plugin.getId());
    }

    @Override
    public VersionDto addVersionToPluginOrUpdateById(Long pluginId, VersionRestDto versionCreateDto) {
        var plugin = getPlugin(pluginId);
        var version = buildVersion(pluginId, versionCreateDto);
        var versions = plugin.getVersions();
        versions.add(version);
        var savedVersion = save(versionToDto(version));
        return savedVersion;
    }

    private Version buildVersion(Long pluginId, VersionRestDto versionCreateDto) {
        return Version.Builder.aVersion()
                .pluginId(pluginId)
                .changelog(versionCreateDto.getChangelog())
                .version(versionCreateDto.getVersion())
                .build();
    }

    private VersionDto versionToDto(Version version) {
        return toDto(version);
    }

    @Override
    public void deletePluginById(Long id) {
        pluginRepositoryPort.deletePluginById(id);
    }

    @Override
    public void deleteVersionById(Long id) {
        pluginRepositoryPort.deleteVersionById(id);
    }

    @Override
    public PluginDto getPluginById(Long id) {
        var plugin = getPlugin(id);
        return toDto(plugin);
    }

    @Override
    public VersionDto getVersionById(Long id) {
        return pluginRepositoryPort.getVersionById(id);
    }

    @Override
    public byte[] dowanloadPluginByVersionId(Long versionId, String token) {
        var version = pluginRepositoryPort.getVersionById(versionId);
        var plugin = getPlugin(version.getPluginId());
        var user = getUserFromToken(token);
        if(Objects.equals(plugin.getType(), PluginType.PREMIUM) && !user.getPlugins().contains(plugin)){
            throw new UserDoesNotHavePlugin(plugin.getId(), user.getEmail());
        }
        var currentClass = this.getClass();
        var resource = currentClass.getResource(PLUGIN_CLASSPATH +version.getFile()+PLUGIN_FORMAT);
        try {
            var stream = resource.openStream();
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new FileReadingException(version.getFile());
        }
    }

    @Override
    public byte[] dowanloadIconByPluginId(long id) {
        var plugin = getPlugin(id);
        var currentClass = this.getClass();
        var resource = currentClass.getResource(ICON_CLASSPATH +plugin.getIcon()+ICON_FORMAT);
        try {
            var stream = resource.openStream();
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new FileReadingException(plugin.getIcon());
        }
    }

    private UserDto getUserFromToken(String token) {
        return getUser(jwtPort.getSubject(token));
    }

    private UserDto getUser(String email) {
        return userRepositoryPort.getUserByEmail(email)
                .orElseThrow(() -> new UserDoesNotExistsException(email));
    }
}
