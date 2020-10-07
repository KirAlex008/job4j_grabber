package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements rows = doc.select(".postslisttopic");
       /* for (Element row : rows) {
            Element href = row.child(0);
            //System.out.println(href.attr("href"));
            //System.out.println(href.text());
            Element date = row.parent().child(5);
            System.out.println(date);
        }*/

        Elements rowData = doc.select(".altCol");
        for (Element elem : rowData) {
            String date = elem.getElementsMatchingText("\\d{2}\\s[а-яА-ЯёЁ]{3}\\s\\d{2},\\s\\d{2}:\\d{2}").text();
            System.out.println(date);
        }

    }
}