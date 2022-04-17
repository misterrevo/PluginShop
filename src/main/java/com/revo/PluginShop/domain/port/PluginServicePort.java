package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginCreateDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginEditDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PluginServicePort {

    List<PluginDto> getAllFreePlugins();
    List<PluginDto> getAllByNameContaining(String name);
    List<PluginDto> getAllPremiumPlugins();
    PluginDto createNewPlugin(PluginCreateDto createDto, MultipartFile pluginFile, MultipartFile iconFile);
    PluginDto changePluginData(PluginEditDto editDto);
    PluginDto changePluginIcon(Long id, MultipartFile iconFile);
    PluginDto updatePluginFile(Long id, MultipartFile pluginFile);
    PluginDto buyPlugin(Long id, String token);
}
