package com.revo.PluginShop.infrastructure.utils;

import com.revo.PluginShop.domain.port.ResourceUtilsPort;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Component
class ResourceUtilsAdapter implements ResourceUtilsPort {
    @Override
    public File getFile(String path) throws FileNotFoundException {
        return ResourceUtils.getFile(path);
    }
}
