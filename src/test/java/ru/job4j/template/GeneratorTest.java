package ru.job4j.template;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertThat;

public class GeneratorTest {
/*    @Test
    public void whenWork() {
        Generator generator = new Generator();
        String template = "I am a ${name}, Who are ${subject}? ";
        Map<String, String> args = new HashMap();
        args.put("name", "Petr Arsentev");
        args.put("subject", "you");
        String rsl = generator.produce(template, args);
        assertThat(rsl, is("I am a Petr Arsentev, Who are you? "));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNotWork() {
        Generator generator = new Generator();
        String template = "I am a ${name}, Who are ${subject}? ";
        Map<String, String> args = new HashMap();
        args.put("name", "Petr Arsentev");
        args.put("subject", "you");
        args.put("object", "car");
        String rsl = generator.produce(template, args);
    }*/
}