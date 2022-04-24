package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.VersionDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginRestDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.VersionRestDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PluginServicePort {

    List<PluginDto> getAllFreePlugins();
    List<PluginDto> getAllByNameContaining(String name);
    List<PluginDto> getAllPremiumPlugins();
    PluginDto createNewPlugin(PluginRestDto createDto);
    PluginDto changePluginData(Long id, PluginRestDto editDto);
    PluginDto changePluginIcon(Long id, MultipartFile iconFile);
    VersionDto updateVersionFile(Long id, MultipartFile pluginFile);
    PluginDto buyPlugin(Long id, String email);
    VersionDto addVersionToPluginOrUpdateById(Long pluginId, VersionRestDto versionCreateDto);
    void deletePluginById(Long id);
    void deleteVersionById(Long id);
    PluginDto getPluginById(Long id);
    VersionDto getVersionById(Long id);
    byte[] downloadPluginByVersionId(Long versionId, String token);
    byte[] downloadIconByPluginId(long id);
}
