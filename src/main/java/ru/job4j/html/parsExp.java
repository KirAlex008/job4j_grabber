package ru.job4j.html;

import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parsExp {
    public static void main(String[] args) throws Exception {
        //15 сен 20, 14:09
    Pattern pattern = Pattern.compile("\\d{2}\\s[а-яА-ЯёЁ]{3}\\s\\d{2},\\s\\d{2}:\\d{2}");
    String input = "20 сен 20, 14:09";
    Matcher matcher = pattern.matcher(input);
    boolean found = matcher.find();
        if(found)
            System.out.println("Найдено");
        else
            System.out.println("Не найдено");
    }
}
