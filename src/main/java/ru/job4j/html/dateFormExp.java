package ru.job4j.html;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class dateFormExp {
    // 2 дек 19, 22:29
    // yyyy-MM-dd HH:mm:ss
    public static void main(String[] args) {
        /*String dateForExp = "2 дек 19, 22:29";
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM yy, HH:mm");
        try {
            Date date = formatter.parse(dateForExp);
            System.out.println(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }*/
        // String testDate = "11 ноя 1980 г.";


        String testDate = "2 дек 19, 22:29";
        String[] months = {"янв", "фев", "мар", "апр", "мая", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
        Locale ru = new Locale("ru");
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(ru);
        symbols.setMonths(months);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yy',' HH:mm", ru);
        format.setDateFormatSymbols(symbols);
        try {
            Date date = format.parse(testDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
