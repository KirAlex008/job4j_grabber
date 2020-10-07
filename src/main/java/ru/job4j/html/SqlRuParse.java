package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements rows = doc.select(".postslisttopic");
        List<String> listNotFormingDateNotStandart = new ArrayList<>();
        List<String> listNotFormingDateStandart = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();
        for (Element row : rows) {
            Element href = row.child(0);
            //System.out.println(href.attr("href"));
            //System.out.println(href.text());
            Element date = row.parent().child(5);
            System.out.println(date);
        }

        Elements rowData = doc.select(".altCol");
        for (Element elem : rowData) {
            String dateNotStandart = elem.getElementsMatchingText("[а-яА-ЯёЁ]{5,},\\s\\d{2}:\\d{2}").text();
            String dateStandart = elem.getElementsMatchingText("\\d{1,}\\s[а-яА-ЯёЁ]{3}\\s\\d{2},\\s\\d{2}:\\d{2}").text();
            if (!dateStandart.equals("")) {
                listNotFormingDateStandart.add(dateStandart);
            }
            if (!dateNotStandart.equals("")) {
                listNotFormingDateNotStandart.add(dateNotStandart);
            }
        }
        for (String elem : listNotFormingDateStandart) {
            Date date = DateFormating.toDateCoinvertStandart(elem);
            dateList.add(date);
        }

        for (String elem : listNotFormingDateNotStandart) {
            Date date = DateFormating.toDateCoinvertNotStandart(elem);
            dateList.add(date);
        }
        for (Date elem : dateList) {
            System.out.println(elem + " DATE");
        }

    }
}