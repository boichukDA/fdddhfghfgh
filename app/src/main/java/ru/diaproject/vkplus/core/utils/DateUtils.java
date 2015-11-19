package ru.diaproject.vkplus.core.utils;


import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.diaproject.vkplus.R;

public abstract class DateUtils {
    public static final SimpleDateFormat  dayPattern = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat  timePattern = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat  yearPattern = new SimpleDateFormat("yyyy");

    public static final String newsDateFormat(Integer date, Context context){
        Long vkDateInlocal = date*1000L;
        Long currentTime = System.currentTimeMillis();
        int diff = new Long(Math.abs(currentTime - vkDateInlocal)).intValue()/1000;

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
                if (Integer.valueOf(yearPattern.format(vkDateInlocal)).equals(Integer.valueOf(yearPattern.format(currentTime))))
                    return new SimpleDateFormat(context.getResources().getString(R.string.news_date_pattern)).format(vkDateInlocal);
                else
                    return new SimpleDateFormat(context.getResources().getString(R.string.news_date_year_pattern)).format(vkDateInlocal);
            }
        }
    }

    public static final String toVideoTimeFormat(Integer duration){
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
}
