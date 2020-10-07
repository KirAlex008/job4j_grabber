package ru.job4j.html;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TodayOrYesterdayConverting {
    // сегодня, 13:03
    // вчера, 18:23
    // "2 дек 19, 22:29";
    public static void main(String[] args) {
        //String day = "сегодня, 13:03";
        Pattern pattern = Pattern.compile("сегодня,\\s\\d{2}:\\d{2}");
        Pattern patternDay = Pattern.compile("\\d{2}:\\d{2}");
        Pattern patternHour = Pattern.compile("\\d{2}:");
        Pattern patternMinute = Pattern.compile(":\\d{2}");
        String input = "сегодня, 13:03";
        Matcher matcher = pattern.matcher(input);
        Matcher matcherDay = patternDay.matcher(input);
        Matcher matcherHour = patternHour.matcher(input);
        Matcher matcherMinute = patternMinute.matcher(input);

        boolean found = matcher.find();
        /*boolean foundDay = matcherDay.find();
        boolean foundHour = matcherHour.find();
        boolean foundMinute = matcherMinute.find();*/
        System.out.println(found);
        /*System.out.println(foundDay);
        System.out.println(foundHour);
        System.out.println(foundMinute);*/
       /* String hour = matcherHour.group();
        String minute = matcherMinute.group();
        String day = matcherDay.group()*/;
        /*System.out.println(hour + "hour");
        System.out.println(minute + "minute");
        System.out.println(day + "day");*/

        Calendar calendar = new GregorianCalendar();
        Date today  = calendar.getInstance().getTime();
        calendar.add(calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();

        System.out.println(today + "TODAY");
        System.out.println(yesterday + "YESTERDAY");
        /*String today = Integer.toString(date1.getDay()) + " " + Integer.toString(date1.getMonth()) + " " +
                Integer.toString(date1.getYear()) ;*/
        //String today = calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH) + " " +
        System.out.println(found);
        //System.out.println(today);
    }
}
