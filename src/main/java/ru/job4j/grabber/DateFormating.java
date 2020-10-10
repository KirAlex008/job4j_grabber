package ru.job4j.grabber;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFormating {
    public static Date toDateCoinvertStandart(String line) {
        Date date = null;
        //String testDate = "2 дек 19, 22:29";
        String[] months = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
        Locale ru = new Locale("ru");
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(ru);
        symbols.setMonths(months);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yy',' HH:mm", ru);
        format.setDateFormatSymbols(symbols);
        try {
            date = format.parse(line);
            //System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date toDateCoinvertNotStandart(String line) {
        Date result = null;
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+3"));
        Pattern patternToday = Pattern.compile("сегодня,\\s\\d{2}:\\d{2}");
        Pattern patternYesterday = Pattern.compile("вчера,\\s\\d{2}:\\d{2}");
        Pattern patternTime = Pattern.compile("\\d{2}:\\d{2}");
        Matcher matcherYesterday = patternYesterday.matcher(line);
        Matcher matcherToday = patternToday.matcher(line);
        Matcher matcherTime = patternTime.matcher(line);
        boolean found = matcherTime.find();
        boolean foundToday = matcherToday.find();
        boolean foundYesterday = matcherYesterday.find();
        if (foundToday) {
            String times = matcherTime.group();
            String[] partsOfTime = times.split(":");
            int hour = Integer.parseInt(partsOfTime[0]);
            int minute = Integer.parseInt(partsOfTime[1]);
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.add(Calendar.HOUR, - 12);
            result = calendar.getTime();
        }
        if (foundYesterday) {
            String times = matcherTime.group();
            String[] partsOfTime = times.split(":");
            int hour = Integer.parseInt(partsOfTime[0]);
            int minute = Integer.parseInt(partsOfTime[1]);
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.add(Calendar.DAY_OF_MONTH, - 1);
            calendar.add(Calendar.HOUR, - 12);
            result = calendar.getTime();
        }
        return result;
    }


}
