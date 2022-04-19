package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.VersionDto;
import com.revo.PluginShop.domain.port.PluginServicePort;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginRestDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.VersionRestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/plugins")
@RequiredArgsConstructor
@Validated
class PluginController {

    private static final String PLUGINS_LOCATION = "/plugins";

    private final PluginServicePort pluginServicePort;

    @GetMapping("/free")
    ResponseEntity<List<PluginDto>> getAllFreePlugins(){
        var plugins = pluginServicePort.getAllFreePlugins();
        return ResponseEntity.ok(plugins);
    }

    @GetMapping
    ResponseEntity<List<PluginDto>> getAllByNameContaining(@RequestParam String name){
        var plugins = pluginServicePort.getAllByNameContaining(name);
        return ResponseEntity.ok(plugins);
    }

    @GetMapping("/premium")
    ResponseEntity<List<PluginDto>> getAllPremiumPlugins(){
        var plugins = pluginServicePort.getAllPremiumPlugins();
        return ResponseEntity.ok(plugins);
    }

    @PostMapping
    ResponseEntity<PluginDto> createNewPlugin(@Valid @RequestBody PluginRestDto createDto, @RequestParam MultipartFile pluginFile, @RequestParam MultipartFile iconFile){
        var plugin = pluginServicePort.createNewPlugin(createDto, pluginFile, iconFile);
        return ResponseEntity.created(URI.create(PLUGINS_LOCATION)).body(plugin);
    }

    @PatchMapping
    ResponseEntity<PluginDto> changePluginData(@Valid @RequestBody PluginRestDto editDto){
        var plugin = pluginServicePort.changePluginData(editDto);
        return ResponseEntity.ok(plugin);
    }

    @PatchMapping("/{id}/icon")
    ResponseEntity<PluginDto> changePluginIcon(@PathVariable long id, @RequestParam MultipartFile iconFile){
        var plugin = pluginServicePort.changePluginIcon(id, iconFile);
        return ResponseEntity.ok(plugin);
    }

    @PatchMapping("/{id}")
    ResponseEntity<PluginDto> changePluginFile(@PathVariable long id, @RequestParam MultipartFile pluginFile){
        var plugin = pluginServicePort.updatePluginFile(id, pluginFile);
        return ResponseEntity.ok(plugin);
    }

    @PatchMapping("/versions")
    ResponseEntity<VersionDto> addVersionToPluginOrUpdateById(@Valid @RequestBody VersionRestDto versionRestDto, @RequestParam MultipartFile pluginFile){
        var version = pluginServicePort.addVersionToPluginOrUpdateById(versionRestDto, pluginFile);
        return ResponseEntity.ok(version);
    }

    @DeleteMapping("/{id}")
    void deletePluginById(@PathVariable long id){
        pluginServicePort.deletePluginById(id);
    }

    @PatchMapping("/versions/{id}")
    void deleteVersionById(@PathVariable long id){
        pluginServicePort.deleteVersionById(id);
    }

    @GetMapping("/{id}")
    ResponseEntity<PluginDto> getPluginById(@PathVariable long id){
        var plugin = pluginServicePort.getPluginById(id);
        return ResponseEntity.ok(plugin);
    }

    @GetMapping("/versions/{id}")
    ResponseEntity<VersionDto> getVersionById(@PathVariable long id){
        var version = pluginServicePort.getVersionById(id);
        return ResponseEntity.ok(version);
    }
}
