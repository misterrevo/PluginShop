package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.infrastructure.application.rest.dto.UserRestDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
class LoginController {

    @PostMapping
    void login(@Valid @RequestBody UserRestDto credentials){}
}
