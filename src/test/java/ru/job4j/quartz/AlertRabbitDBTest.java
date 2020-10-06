package ru.job4j.quartz;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class AlertRabbitDBTest {

    public Connection init() {
        try (InputStream in = AlertRabbitDB.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws Exception {
        try (AlertRabbitDB store = new AlertRabbitDB(ConnectionRollback.create(this.init()))) {
            long time = System.currentTimeMillis();
            store.add(time);
            int result = store.findAll().size();
            assertThat(result, is(1));
        }
    }
}
