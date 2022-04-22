package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.MysqlContainer;
import com.revo.PluginShop.infrastructure.application.rest.dto.PluginRestDto;
import com.revo.PluginShop.infrastructure.application.rest.dto.VersionRestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

import static com.revo.PluginShop.MysqlContainer.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class PluginControllerTest extends MysqlContainer {

    private static final String GET_ALL_FREE_PLUGINS_END_POINT = "/plugins/free";
    private static final String ARRAY_JSON_PATH = "$.[0].name";
    private static final String PLUGINS_END_POINT = "/plugins";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_NAME_VALUE = "free";
    private static final String GET_ALL_PREMIUM_PLUGINS_END_POINT = "/plugins/premium";
    private static final String PLUGIN_CREATE_NAME = "CREATED";
    private static final String PLUGIN_CREATE_TYPE = "FREE";
    private static final String PLUGIN_CREATE_VIDEO = "NONE";
    private static final String NAME_JSON_PATH = "$.name";
    private static final String MINECRAFT_VERSION_JSON_PATH = "$.minecraftVersion";
    private static final String DESCRIPTION_JSON_PATH = "$.description";
    private static final String PRICE_JSON_PATH = "$.price";
    private static final String TYPE_JSON_PATH = "$.type";
    private static final String VIDEO_JSON_PATH = "$.videoUrl";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String PLUGINS_CHANGE_END_POINT = "/plugins/3";
    private static final String PLUGIN_CHANGE_NAME = "CHANGED";
    private static final String ICON_NAME = "icon.png";
    private static final String ICON_JSON_PATH = "$.icon";
    private static final String PLUGINS_CHANGE_ICON_END_POINT = "/plugins/3/icons";
    private static final String THROW_PLUGINS_CHANGE_ICON_END_POINT = "/plugins/-1/icons";
    private static final String PLUGIN_NAME = "plugin.jar";
    private static final String VERSIONS_CHANGE_FILE_END_POINT = "/plugins/versions/1/files";
    private static final String FILE = "file";
    private static final String FILE_JSON_PATH = "$.file";
    private static final String THROW_VERSIONS_CHANGE_FILE_END_POINT = "/plugins/versions/-1/files";
    private static final String VERSION_NAME = "0.0.2";
    private static final String VERSION_CHANGELOG = "CHANGELOG";
    private static final String PLUGINS_ADD_VERSION_END_POINT = "/plugins/1/versions";
    private static final String VERSION_JSON_PATH = "$.version";
    private static final String CHANGELOG_JSON_PATH = "$.changelog";
    private static final String DELETE_END_POINT = "/plugins/4";
    private static final String DELETE_VERSION_END_POINT = "/plugins/versions/2";
    private static final String GET_END_POINT = "/plugins/1";
    private static final Object TEST_NAME = "test";
    private static final String GET_VERSION_END_POINT = "/plugins/versions/1";
    private static final Object VERSION_GET = "0.0.1";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetAllFreePlugins() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get(URI.create(GET_ALL_FREE_PLUGINS_END_POINT)))
                .andExpect(jsonPath(ARRAY_JSON_PATH).exists())
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllByNameContaining() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get(URI.create(PLUGINS_END_POINT))
                .param(PARAM_NAME, PARAM_NAME_VALUE))
                .andExpect(jsonPath(ARRAY_JSON_PATH).exists())
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllPremiumPlugins() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get(URI.create(GET_ALL_PREMIUM_PLUGINS_END_POINT)))
                .andExpect(jsonPath(ARRAY_JSON_PATH).exists())
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldCreateNewPlugin() throws Exception {
        //given
        var pluginRestDto = PluginRestDto.builder()
                .description(PLUGIN_CREATE_NAME)
                .minecraftVersion(1.18)
                .name(PLUGIN_CREATE_NAME)
                .price(0.00)
                .type(PLUGIN_CREATE_TYPE)
                .videoUrl(PLUGIN_CREATE_VIDEO)
                .build();
        var jsonString = objectMapper.writeValueAsString(pluginRestDto);
        //when
        //then
        mockMvc.perform(post(URI.create(PLUGINS_END_POINT))
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonString))
                .andExpect(jsonPath(NAME_JSON_PATH).value(PLUGIN_CREATE_NAME))
                .andExpect(jsonPath(MINECRAFT_VERSION_JSON_PATH).value(1.18))
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(PLUGIN_CREATE_NAME))
                .andExpect(jsonPath(PRICE_JSON_PATH).value(0.00))
                .andExpect(jsonPath(TYPE_JSON_PATH).value(PLUGIN_CREATE_TYPE))
                .andExpect(jsonPath(VIDEO_JSON_PATH).value(PLUGIN_CREATE_VIDEO))
                .andExpect(status().is(201));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldThrowWhileCreatingNewPlugin() throws Exception{
        //given
        var pluginRestDto = PluginRestDto.builder()
                .description(null)
                .minecraftVersion(1.18)
                .name(PLUGIN_CREATE_NAME)
                .price(0.00)
                .type(PLUGIN_CREATE_TYPE)
                .videoUrl(PLUGIN_CREATE_VIDEO)
                .build();
        var jsonString = objectMapper.writeValueAsString(pluginRestDto);
        //when
        //then
        mockMvc.perform(post(URI.create(PLUGINS_END_POINT))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonString))
                .andExpect(status().is(400));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldChangePluginData() throws Exception{
        //given
        var pluginRestDto = PluginRestDto.builder()
                .description(PLUGIN_CHANGE_NAME)
                .minecraftVersion(1.18)
                .name(PLUGIN_CHANGE_NAME)
                .price(0.00)
                .type(PLUGIN_CREATE_TYPE)
                .videoUrl(PLUGIN_CREATE_VIDEO)
                .build();
        var jsonString = objectMapper.writeValueAsString(pluginRestDto);
        //when
        //then
        mockMvc.perform(patch(URI.create(PLUGINS_CHANGE_END_POINT))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonString))
                .andExpect(jsonPath(NAME_JSON_PATH).value(PLUGIN_CREATE_NAME))
                .andExpect(jsonPath(MINECRAFT_VERSION_JSON_PATH).value(1.18))
                .andExpect(jsonPath(DESCRIPTION_JSON_PATH).value(PLUGIN_CREATE_NAME))
                .andExpect(jsonPath(PRICE_JSON_PATH).value(0.00))
                .andExpect(jsonPath(TYPE_JSON_PATH).value(PLUGIN_CREATE_TYPE))
                .andExpect(jsonPath(VIDEO_JSON_PATH).value(PLUGIN_CREATE_VIDEO))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldThrowWhileChangingPluginData() throws Exception{
        //given
        //when
        //then
        //given
        var pluginRestDto = PluginRestDto.builder()
                .description(null)
                .minecraftVersion(1.18)
                .name(PLUGIN_CREATE_NAME)
                .price(0.00)
                .type(PLUGIN_CREATE_TYPE)
                .videoUrl(PLUGIN_CREATE_VIDEO)
                .build();
        var jsonString = objectMapper.writeValueAsString(pluginRestDto);
        //when
        //then
        mockMvc.perform(patch(URI.create(PLUGINS_CHANGE_END_POINT))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(jsonString))
                .andExpect(status().is(400));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldChangePluginIcon() throws Exception{
        //given
        var iconFile = new MockMultipartFile(ICON_NAME, new byte[0]);
        //when
        //then
        mockMvc.perform(multipart(PLUGINS_CHANGE_ICON_END_POINT).file(FILE, iconFile.getBytes()))
                .andExpect(jsonPath(ICON_JSON_PATH).exists())
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldThrowWhileChanginPluginIcon() throws Exception{
        //given
        var iconFile = new MockMultipartFile(ICON_NAME, new byte[0]);
        //when
        //then
        mockMvc.perform(multipart(THROW_PLUGINS_CHANGE_ICON_END_POINT))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldChangeVersionFile() throws Exception{
        //given
        var pluginFile = new MockMultipartFile(PLUGIN_NAME, new byte[0]);
        //when
        //then
        mockMvc.perform(multipart(VERSIONS_CHANGE_FILE_END_POINT)
                .file(FILE, pluginFile.getBytes()))
                .andExpect(jsonPath(FILE_JSON_PATH).exists())
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldThrowWhileChaningVersionFile() throws Exception {
        //given
        var pluginFile = new MockMultipartFile(PLUGIN_NAME, new byte[0]);
        //when
        //then
        mockMvc.perform(multipart(THROW_VERSIONS_CHANGE_FILE_END_POINT)
                        .file(FILE, pluginFile.getBytes()))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void addVersionToPluginOrUpdateById() throws Exception{
        //given
        var versionRestDto = VersionRestDto.builder()
                .version(VERSION_NAME)
                .changelog(VERSION_CHANGELOG)
                .build();
        var jsonString = objectMapper.writeValueAsString(versionRestDto);
        //when
        //then
        mockMvc.perform(patch(URI.create(PLUGINS_ADD_VERSION_END_POINT))
                .contentType(APPLICATION_JSON_UTF8)
                .content(jsonString))
                .andExpect(jsonPath(VERSION_JSON_PATH).value(VERSION_NAME))
                .andExpect(jsonPath(CHANGELOG_JSON_PATH).value(VERSION_CHANGELOG))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(roles = ADMIN_ROLE)
    void shouldDeletePluginById() throws Exception{
        //given
        //when
        mockMvc.perform(delete(URI.create(DELETE_END_POINT)));
        //then
        mockMvc.perform(get(DELETE_END_POINT))
                .andExpect(status().is(404));
    }

    @Test
    void deleteVersionById() throws Exception{
        //given
        //when
        mockMvc.perform(patch(URI.create(DELETE_VERSION_END_POINT)));
        //then
        mockMvc.perform(get(DELETE_VERSION_END_POINT))
                .andExpect(status().is(404));
    }

    @Test
    void getPluginById() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get(URI.create(GET_END_POINT)))
                .andExpect(jsonPath(NAME_JSON_PATH).value(TEST_NAME))
                .andExpect(status().is(200));
    }

    @Test
    void getVersionById() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get(URI.create(GET_VERSION_END_POINT)))
                .andExpect(jsonPath(VERSION_JSON_PATH).value(VERSION_GET));
    }

    @Test
    void dowanloadFile() {
        //given
        //when
        //then
    }

    @Test
    void dowanloadIcon() {
        //given
        //when
        //then
    }
}