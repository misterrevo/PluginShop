package com.revo.PluginShop.infrastructure.utils;

import com.revo.PluginShop.domain.port.EncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class BCryptPasswordEncoderAdapter implements EncoderPort {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public String encode(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}
