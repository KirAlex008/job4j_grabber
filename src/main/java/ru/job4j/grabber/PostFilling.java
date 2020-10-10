package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostFilling {
    private static Date date;
    private final static Pattern patternStandart = Pattern.compile("\\d{1,}\\s[а-яА-ЯёЁ]{3}\\s\\d{2},\\s\\d{2}:\\d{2}");
    private final static Pattern patternNotStandart = Pattern.compile("[а-яА-ЯёЁ]{5,},\\s\\d{2}:\\d{2}");

    public static void main(String[] args) throws Exception {
        //String link = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t"; // standart date
        String link = "https://www.sql.ru/forum/1329781/vakansiya-administrator-baz-dannyh-linux-moskva-udalennaya-rabota"; // not standart date
        Post post = PostFilling.createPost(link);
        System.out.println(post.getThema());
        System.out.println(post.getAuthor());
        System.out.println(post.getAnnouncementText());
        System.out.println(post.getLink());
        System.out.println(post.getDate());
    }

    public static Post createPost (String link) throws Exception{
        Document doc = Jsoup.connect(link).get();
        Elements rowsTheme = doc.child(0).getElementsByTag("title");
        //Element themeElem = rowsTheme.get(0);
        String theme = rowsTheme.get(0).text();
        Elements rowsAnnouncementText = doc.child(0).getElementsByTag("td");
        //Element announcementTextElem = rowsAnnouncementText.get(7);
        String announcementText = rowsAnnouncementText.get(7).text();
        Elements rowsAuthor = doc.select(".msgBody");
        //Element author = rowsAuthor.get(0);
        String authorText = rowsAuthor.get(0).text();
        //String authorLink = rowsAuthor.get(0).child(0).attr("href");
        Elements rowsDate = doc.select(".msgFooter");
        Element dateString = rowsDate.get(0);
        Matcher matcherStandart = patternStandart.matcher(dateString.text());
        boolean found = matcherStandart.find();
        if(found) {
            String elemDate = matcherStandart.group();
            date = DateFormating.toDateCoinvertStandart(elemDate);
        } else {
            Matcher matcherNotStandart = patternNotStandart.matcher(dateString.text());
            matcherNotStandart.find();
            String elemDate = matcherNotStandart.group();
            date = DateFormating.toDateCoinvertNotStandart(elemDate);
        }
        Post post = new Post();
        post.setThema(theme);
        post.setAnnouncementText(announcementText);
        post.setAuthor(authorText);
        post.setLink(link);
        post.setDate(date);
        return post;
    }
}
