package ru.diaproject.vkplus.core.utils;


import android.annotation.SuppressLint;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.diaproject.vkplus.R;

public abstract class DateUtils {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat  dayPattern = new SimpleDateFormat("yyyyMMdd");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat  timePattern = new SimpleDateFormat("HH:mm");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat  yearPattern = new SimpleDateFormat("yyyy");

    private static SimpleDateFormat newsDatePattern;
    private static SimpleDateFormat newsDateYearPattern;
    private static SimpleDateFormat bDateFullOutPattern = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
    private static SimpleDateFormat bDateShortOutPattern = new SimpleDateFormat("d MMMM", Locale.getDefault());
    private static SimpleDateFormat bDateFullInPattern = new SimpleDateFormat("d.M.yyyy", Locale.getDefault());
    private static SimpleDateFormat bDateShortInPattern = new SimpleDateFormat("d.M", Locale.getDefault());

    public static  String newsDateFormat(Integer date, Context context){
        Long vkDateInlocal = date*1000L;
        Long currentTime = System.currentTimeMillis();
        int diff =  Long.valueOf(Math.abs(currentTime - vkDateInlocal)).intValue()/1000;

        if (diff < 60)
            return context.getResources().getString(R.string.just_now) ;

        diff = diff/60;

        if (diff < 60)
            return context.getResources().getQuantityString(R.plurals.news_minutes_variants, diff, diff);

        diff = diff/60;

        if (diff < 4)
           return context.getResources().getQuantityString(R.plurals.news_hour_variants, diff, diff);
        else {
            String strVkDateInLocal = dayPattern.format(new Date(vkDateInlocal));
            String strCurrentTime = dayPattern.format(new Date(currentTime));
            if (strVkDateInLocal.equals(strCurrentTime))
                return context.getResources().getString(R.string.news_today_at) + " " + timePattern.format(vkDateInlocal);
            else {
                Long vkCompareInt = Long.valueOf(strVkDateInLocal);
                Long currentTimeInt = null;

                try{
                    currentTimeInt = Long.valueOf(strCurrentTime);
                }catch (NumberFormatException ex){
                    ex.printStackTrace();
                }

                if (vkCompareInt.equals(currentTimeInt-1))
                    return context.getResources().getString(R.string.news_yesterday_at) + " " + timePattern.format(vkDateInlocal);
                else
                if (Integer.valueOf(yearPattern.format(vkDateInlocal)).equals(Integer.valueOf(yearPattern.format(currentTime)))){
                    if (newsDatePattern == null)
                        newsDatePattern = new SimpleDateFormat(context.getResources().getString(R.string.news_date_pattern), Locale.getDefault());
                    return  newsDatePattern.format(vkDateInlocal);

                }
                else {
                    if (newsDateYearPattern == null)
                        newsDateYearPattern = new SimpleDateFormat(context.getResources().getString(R.string.news_date_year_pattern),Locale.getDefault());
                    return newsDateYearPattern.format(vkDateInlocal);
                }
            }
        }
    }

    public static String toVideoTimeFormat(Integer duration){
        int seconds;
        int minutes;
        int hours;
        seconds = duration%60;
        duration/=60;
        minutes = duration%60;
        duration/=60;
        hours = duration;

        StringBuilder result = new StringBuilder("");

        if (hours != 0)
           result.append(hours).append(":");

        if (minutes<10)
            result.append("0");
        result.append(minutes).append(":");

        if (seconds<10)
            result.append("0");
        result.append(seconds);

        return result.toString();
    }

    public static String parseBDateString(String bDate) {
        if (bDate.length()<=6)
            return parseDateShort(bDate);
       else
            return parseDateFull(bDate);
    }
    private static String parseDateShort(String bDate){
        try {
            Date date = bDateShortInPattern.parse(bDate);
            return bDateShortOutPattern.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String parseDateFull(String bDate){
        try {
            Date date = bDateFullInPattern.parse(bDate);
            return bDateFullOutPattern.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
