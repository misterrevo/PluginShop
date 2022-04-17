package com.revo.PluginShop.infrastructure.encoder;

import com.revo.PluginShop.domain.port.EncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
