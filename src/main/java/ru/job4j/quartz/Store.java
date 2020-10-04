package ru.job4j.quartz;

import java.sql.Connection;
import java.util.List;

public interface Store extends AutoCloseable {
    Connection init();
    void add(long time);
    List<Long> findAll();
}