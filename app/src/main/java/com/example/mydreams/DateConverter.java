package com.example.mydreams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    public static String convert(long date, String mode) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df;
        Date d = new Date(date);
        calendar.setTime(d);
        switch (mode) {
            case "week":
                df = new SimpleDateFormat("EEE", Locale.getDefault());
                break;
            case "day":
                df = new SimpleDateFormat("dd", Locale.getDefault());
                break;
            case "month":
                df = new SimpleDateFormat("MM", Locale.getDefault());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }

        return df.format(calendar.getTime());
    }

    public static long getTimeInMillisFromString(String currentDate) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        try {
            date = df.parse(currentDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            calendar.setTime(date);
        }

        return calendar.getTimeInMillis();
    }

    public static String getStringFromTimeInMillis(long date) {
        Calendar calendar = Calendar.getInstance();
        Date convertedDate = new Date(date);
        calendar.setTime(convertedDate);
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        return df.format(calendar.getTime());
    }

    public static String getTimeFromMillis(long millis) {
        Calendar calendar = Calendar.getInstance();
        Date convertedDate = new Date(millis);
        calendar.setTime(convertedDate);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());

        return df.format(calendar.getTime());
    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return df.format(calendar.getTime());
    }

    public static String getClosestDate(String currentDate, int amount) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        try {
            date = df.parse(currentDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, amount);
        }

        return df.format(calendar.getTime());
    }
}
