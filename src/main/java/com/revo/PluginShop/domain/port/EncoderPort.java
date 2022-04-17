package com.revo.PluginShop.domain.port;

public interface EncoderPort {

    boolean matches(String rawPassword, String encodedPassword);
    String encode(String rawPassword);
}
