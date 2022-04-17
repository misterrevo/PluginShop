package com.revo.PluginShop.domain.port;

public interface JwtPort {

    String getEmailFromToken(String token);
    String createTokenByEmail(String email);
}
