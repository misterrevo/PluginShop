package com.revo.PluginShop.domain.port;

import com.revo.PluginShop.domain.dto.ProjectDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.ProjectRestDto;

import java.util.List;

public interface ProjectServicePort {

    ProjectDto processProjectAsk(ProjectRestDto projectRestDto);
}
