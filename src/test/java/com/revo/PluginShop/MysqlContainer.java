package com.revo.PluginShop;

import com.revo.PluginShop.infrastructure.application.PluginShopApplication;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.sql.Connection;

@SpringBootTest(classes = PluginShopApplication.class)
public abstract class MysqlContainer {

    private static final MySQLContainer mySQLContainer;
    private static final String SPRING_DB_URL_PROPERTY = "spring.datasource.url";
    private static final String SPRING_DB_PASSWORD_PROPERTY = "spring.datasource.password";
    private static final String SPRING_DB_USERNAME_PROPERTY = "spring.datasource.username";
    private static final String SPRING_DB_DRIVER_CLASS_NAME_PROPERTY = "spring.datasource.driverClassName";
    private static final String CHARSET_NAME = "utf8";
    private static boolean imported = false;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName(CHARSET_NAME));

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws Exception{
       if(!imported){
           try (Connection connection = dataSource.getConnection()) {
               ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/users_import.sql"));
               ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/plugins_import.sql"));
               ScriptUtils.executeSqlScript(connection, new ClassPathResource("database/versions_import.sql"));
               imported = true;
           }
       }
    }

    static {
        mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:8.0")
                        .withReuse(true);
        mySQLContainer.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add(SPRING_DB_URL_PROPERTY, mySQLContainer::getJdbcUrl);
        registry.add(SPRING_DB_PASSWORD_PROPERTY, mySQLContainer::getPassword);
        registry.add(SPRING_DB_USERNAME_PROPERTY, mySQLContainer::getUsername);
        registry.add(SPRING_DB_DRIVER_CLASS_NAME_PROPERTY, mySQLContainer::getDriverClassName);
    }
}