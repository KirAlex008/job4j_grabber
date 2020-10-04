package ru.job4j.quartz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class FileConfigReader {

    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public FileConfigReader(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().filter(line -> line.contains("="))
                    .map(s -> s.split("=", 2))
                    .forEach(s -> values.put(s[0],s[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }
}