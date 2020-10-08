package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FivePagePasrs {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        List<String> linksList = new ArrayList<>();
        Elements lines = doc.select(".sort_options");
            for (Element line : lines) {
                Elements content = line.getElementsByAttribute("href");
                for (Element el : content) {
                    linksList.add(el.attr("href"));
                    System.out.println(el.attr("href"));
                }
            }
        for (int i = 0; i < 5; i++) {
            String link = linksList.get(i);
            Document docNext = Jsoup.connect(link).get();
            Elements rows = docNext.select(".postslisttopic");
            for (Element row : rows) {
                Element href = row.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                Element date = row.parent().child(5);
                System.out.println(date);
            }
        }
    }
}
