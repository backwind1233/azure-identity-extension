package com.example.demo.identity.fakedb.jdbc.test;

import com.example.demo.identity.fakedb.jdbc.FakeDBJDBCDriver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import javax.sql.DataSource;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
public class FakeDBSpringTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class))

        .withPropertyValues("spring.datasource.url:jdbc:fakedb://test1","spring.datasource.driverClassName:"+ FakeDBJDBCDriver.class.getName());

    @Test
    void testDefaultDataSourceExists() {
        this.contextRunner.run((context) -> assertThat(context).hasSingleBean(DataSource.class));
    }

}
