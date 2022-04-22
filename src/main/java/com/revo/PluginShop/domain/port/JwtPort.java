package com.revo.PluginShop.domain.port;

public interface JwtPort {

    String getSubject(String token);
    String getToken(String subject);
}
