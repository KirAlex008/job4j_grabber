package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValueOfPasrsingPages {

    private final static Pattern patternExeption = Pattern.compile("(shpargalki)|" +
            "(soobshheniya-ot-moderatorov-zdes-vy-mozhete-uznat-prichiny-udaleniya-topikov)|" +
            "(https://www.sql.ru/forum/484798/pravila-foruma) | (484798)");


        public List<String> gettingLinksOfPages(String linkOfMainPage) throws Exception {
            List<String> listOfPageLinks = new ArrayList<>();
            Document doc = Jsoup.connect(linkOfMainPage).get();
            Elements lines = doc.select(".sort_options");
            for (Element line : lines) {
                Elements content = line.getElementsByAttribute("href");
                for (Element el : content) {
                    listOfPageLinks.add(el.attr("href"));
                }
            }
            return listOfPageLinks;
        }

    public List<String> gettingLinksOfAnnouncment(List<String> listOfPageLinks) throws Exception {
        List<String> listOfPostsLinks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String link = listOfPageLinks.get(i);
            Document docNext = Jsoup.connect(link).get();
            Elements rows = docNext.select(".postslisttopic");
            for (Element row : rows) {
                Element href = row.child(0);
                listOfPostsLinks.add(href.attr("href"));
            }

            listOfPostsLinks.remove("https://www.sql.ru/forum/484798/pravila-foruma");
            listOfPostsLinks.remove("https://www.sql.ru/forum/1196621/shpargalki");
            listOfPostsLinks.remove("https://www.sql.ru/forum/485068/soobshheniya-ot-moderatorov-zdes-vy-mozhete-uznat-prichiny-udaleniya-topikov");
        }
        for (int i =0; i < listOfPostsLinks.size(); i++) {
            Matcher matcher = patternExeption.matcher(listOfPostsLinks.get(i));
            boolean found = matcher.find();
            if(found) {
                listOfPostsLinks.remove(i);
            }
        }
        return listOfPostsLinks;
    }

    public static void main(String[] args) throws Exception {
        String link = "https://www.sql.ru/forum/job-offers";
        ValueOfPasrsingPages contener = new ValueOfPasrsingPages();
        List<String> list = contener.gettingLinksOfAnnouncment(contener.gettingLinksOfPages(link));
        for (var el : list) {
            System.out.println(el);
        }
    }
}
