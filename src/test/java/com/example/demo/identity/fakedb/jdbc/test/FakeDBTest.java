package com.example.demo.identity.fakedb.jdbc.test;

import com.example.demo.identity.AuthProperty;
import com.example.demo.identity.fakedb.jdbc.DriverConstant;
import com.example.demo.identity.fakedb.jdbc.FakeDBJDBCDriver;
import com.example.demo.identity.fakedb.jdbc.auth.FakeDatabaseJDBCAuthPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

public class FakeDBTest {
    String jdbcurl = "jdbc:fakedb://localhost:3306/testdb";
    private Driver driver;
    private Properties properties;

    @BeforeEach
    public void setup() throws Exception {
      driver =  DriverManager.getDriver(jdbcurl);
//        driver = new FakeDBJDBCDriver();
        properties = new Properties();
        properties.setProperty("jdbcUrl", jdbcurl);
        properties.setProperty("user", "testuser");
    }

    @Test
    public void testWithUsernameAndPassword() throws Exception {
        properties.setProperty("password", "testpassword");
        driver.connect(jdbcurl, properties);
    }

    @Test void testWithoutPasswordButAuthPlugin() throws Exception{
        properties.setProperty(DriverConstant.AUTH_PLUGIN_NAME, FakeDatabaseJDBCAuthPlugin.class.getName());
        driver.connect(jdbcurl, properties);
    }

    @Test void testWithAuthPluginAndProperties() throws Exception{
        properties.setProperty(DriverConstant.AUTH_PLUGIN_NAME, FakeDatabaseJDBCAuthPlugin.class.getName());
//        properties.setProperty(DriverConstant.AUTH_PLUGIN_PROPERTIES, "azure.authority.host=localhost");
        driver.connect(jdbcurl, properties);
    }

    @Test
    void test_without_password_using_pool() throws Exception {
        properties.setProperty(DriverConstant.AUTH_PLUGIN_NAME, FakeDatabaseJDBCAuthPlugin.class.getName());

        int pooledConnection = 30;
        for(int i = 0;i<pooledConnection;i++){
            FakeDBJDBCDriver d = new FakeDBJDBCDriver();
            d.connect(jdbcurl, properties);
        }

    }

    @Test
    void test_without_password_using_pool_and_enable_cache() throws Exception {
        properties.setProperty(DriverConstant.AUTH_PLUGIN_NAME, FakeDatabaseJDBCAuthPlugin.class.getName());
        AuthProperty.CACHE_ENABLED.setProperty(properties, "true");

        int pooledConnection = 30;
        for(int i = 0;i<pooledConnection;i++){
            FakeDBJDBCDriver d = new FakeDBJDBCDriver();
            d.connect(jdbcurl, properties);
        }

    }
}
