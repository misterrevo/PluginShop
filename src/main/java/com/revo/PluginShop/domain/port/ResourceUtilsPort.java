package com.revo.PluginShop.domain.port;

import java.io.File;
import java.io.FileNotFoundException;

public interface ResourceUtilsPort {

    File getFile(String path) throws FileNotFoundException;
}
