package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.dto.UserDto;
import com.revo.PluginShop.domain.port.UserServicePort;
import com.revo.PluginShop.infrastructure.application.rest.dto.UserRestDto;
import com.revo.PluginShop.infrastructure.security.annotation.ForAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
class UserController {

    private static final String USERS_LOCATION = "/users";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserServicePort userServicePort;

    @GetMapping("/{email}")
    @ForAdmin
    ResponseEntity<UserDto> getUserByEmail(@Valid @Email @PathVariable String email){
        var userDto = userServicePort.getUserByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    ResponseEntity<UserDto> getUserByToken(@RequestHeader(AUTHORIZATION_HEADER) String token){
        var userDto = userServicePort.getUserByToken(token);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    ResponseEntity<UserDto> createNewUser(@Valid @RequestBody UserRestDto userCreateDto){
        var userDto = userServicePort.createNewUser(userCreateDto);
        return ResponseEntity.created(URI.create(USERS_LOCATION)).body(userDto);
    }

    @PatchMapping("/{email}/{status}")
    @ForAdmin
    ResponseEntity<UserDto> changeBlockStatus(@Valid @Email @PathVariable String email, boolean status){
        var userDto = userServicePort.changeBlockStatus(email, status);
        return ResponseEntity.ok(userDto);
    }
}
