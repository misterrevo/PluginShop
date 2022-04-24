package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.dto.ProjectDto;
import com.revo.PluginShop.domain.port.ProjectServicePort;
import com.revo.PluginShop.infrastructure.application.rest.dto.ProjectRestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Validated
class ProjectController {

    private final ProjectServicePort projectServicePort;

    @PostMapping
    ResponseEntity<ProjectDto> processProjectAsk(@Valid @RequestBody ProjectRestDto projectRestDto){
        var project = projectServicePort.processProjectAsk(projectRestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
}
