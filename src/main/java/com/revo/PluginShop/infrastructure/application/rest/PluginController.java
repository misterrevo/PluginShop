package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.dto.VersionDto;
import com.revo.PluginShop.domain.exception.FileReadingException;
import com.revo.PluginShop.domain.port.PluginServicePort;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginRestDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.VersionRestDto;
import com.revo.PluginShop.infrastructure.security.annotation.ForAdmin;
import com.revo.PluginShop.infrastructure.security.annotation.ForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/plugins")
@RequiredArgsConstructor
@Validated
class PluginController {

    private static final String PLUGINS_LOCATION = "/plugins";
    private static final String AUTHORIZATION_HEADER = "Authorization";

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
    @ForAdmin
    ResponseEntity<PluginDto> createNewPlugin(@Valid @RequestBody PluginRestDto createDto){
        var plugin = pluginServicePort.createNewPlugin(createDto);
        return ResponseEntity.created(URI.create(PLUGINS_LOCATION)).body(plugin);
    }

    @PutMapping("/{id}")
    @ForAdmin
    ResponseEntity<PluginDto> changePluginData(@PathVariable long id, @Valid @RequestBody PluginRestDto editDto){
        var plugin = pluginServicePort.changePluginData(id, editDto);
        return ResponseEntity.ok(plugin);
    }

    @PostMapping("/{id}/icons")
    @ForAdmin
    ResponseEntity<PluginDto> changePluginIcon(@PathVariable long id, @RequestParam MultipartFile iconFile){
        var plugin = pluginServicePort.changePluginIcon(id, iconFile);
        return ResponseEntity.ok(plugin);
    }

    @PostMapping("/versions/{id}/files")
    @ForAdmin
    ResponseEntity<VersionDto> changeVersionFile(@PathVariable long id, @RequestParam MultipartFile pluginFile){
        var version = pluginServicePort.updateVersionFile(id, pluginFile);
        return ResponseEntity.ok(version);
    }

    @PutMapping("/{id}/versions")
    @ForAdmin
    ResponseEntity<VersionDto> addVersionToPluginOrUpdateById(@PathVariable long id, @Valid @RequestBody VersionRestDto versionRestDto){
        var version = pluginServicePort.addVersionToPluginOrUpdateById(id, versionRestDto);
        return ResponseEntity.ok(version);
    }

    @DeleteMapping("/{id}")
    @ForAdmin
    void deletePluginById(@PathVariable long id){
        pluginServicePort.deletePluginById(id);
    }

    @PutMapping("/versions/{id}")
    @ForAdmin
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

    @GetMapping("/versions/{id}/files")
    @ForUser
    ResponseEntity<byte[]> downloadFile(@RequestHeader(AUTHORIZATION_HEADER) String token, @PathVariable long id){
        var resource = pluginServicePort.dowanloadPluginByVersionId(id, token);
        var plugin = pluginServicePort.getPluginById(id);
        return ResponseEntity.ok()
                .header("Content-Type", "multipart/form-data")
                .header("Content-Disposition", "attachment; filename="+plugin.getName()+".jar")
                .body(resource);
    }

    @GetMapping("/{id}/icons")
    ResponseEntity<byte[]> downloadIcon(@PathVariable long id){
        var resource = pluginServicePort.dowanloadIconByPluginId(id);
        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(resource);
    }
}
