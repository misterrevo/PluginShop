package com.revo.PluginShop.domain;

import com.revo.PluginShop.domain.dto.ProjectDto;
import com.revo.PluginShop.domain.port.MailSenderPort;
import com.revo.PluginShop.domain.port.ProjectServicePort;
import com.revo.PluginShop.infrastructure.application.rest.dto.ProjectRestDto;
import org.springframework.beans.factory.annotation.Value;

public class ProjectService implements ProjectServicePort {

    private static final String TITLE = "RevoPluginShop Project Ask";
    private static final String MESSAGE = "Dostałeś nową propozycję stworzenia pluginu od %s (tel. %s)! Opis projektu: %s";
    private static final String ADMIN_EMAIL = "kwolny31@gmail.com";

    private final MailSenderPort mailSenderPort;

    public ProjectService(MailSenderPort mailSenderPort) {
        this.mailSenderPort = mailSenderPort;
    }

    @Override
    public ProjectDto processProjectAsk(ProjectRestDto projectRestDto) {
        var projectDto = buildProjectDto(projectRestDto);
        mailSenderPort.sendEmail(ADMIN_EMAIL, TITLE, MESSAGE.formatted(projectDto.getEmail(), projectDto.getPhone(), projectDto.getDescription()));
        return projectDto;
    }

    private ProjectDto buildProjectDto(ProjectRestDto projectRestDto) {
        return ProjectDto.Builder.aProjectDto()
                .phone(projectRestDto.getPhone())
                .email(projectRestDto.getEmail())
                .description(projectRestDto.getDescription())
                .build();
    }
}
