package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.port.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {

    private final UserServicePort userServicePort;
}
