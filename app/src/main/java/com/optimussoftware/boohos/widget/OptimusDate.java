package com.optimussoftware.boohos.widget;

import android.content.Context;
import android.util.Log;

import com.optimussoftware.boohos.R;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.util.Commons;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by guerra on 13/09/16.
 * dateStar -> past time
 * dateNow -> actual time
 */
public class OptimusDate {

    private static String tag = "OptimusDate";

    public static DateTime convertStringToDateTime(String dateString) {
        return convertStringToDateTime(Constants.DATE_FORMAT, dateString);
    }

    public static DateTime convertStringToDateTime(String dateFormat, String dateString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat).withLocale(Locale.US);
        return formatter.parseDateTime(dateString);
    }

    public static Date convertStringToDate(String dateString) {
        return convertStringToDate(Constants.DATE_FORMAT, dateString);
    }

    public static Date convertStringToDate(String dateFormat, String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date fechaDate = null;
        try {
            fechaDate = format.parse(dateString);
        } catch (ParseException e) {
            Log.e(tag, "convertStringToDate -> ParseException " + e.toString());
        }
        return fechaDate;
    }

    public static String convertLongToDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String timeAgo(Context c, long dateStar) {
        return timeAgo(c, dateStar, new DateTime().getMillis());
    }

    public static String timeAgo(Context c, long dateStar, long dateEnd) {
        // JodaTime
        //DateTime fecha = new DateTime(2015, 1, 31, 12, 00,
        //        DateTimeZone.forID("Europe/Madrid"));
        //Log.d(tag, "--------------------JodaTime--------------------");
        //Log.d(tag, "Hora Madrid:\t\t %s\n" + fecha);
        //Log.d(tag, "Hora Buenos Aires:\t %s\n" + fecha.withZone(DateTimeZone.forID("America/Argentina/Buenos_Aires")));

        Period p = new Period(dateStar, dateEnd);
        //Log.d(tag, " periodDefault ---> " + PeriodFormat.getDefault().print(p));

        if (p.getYears() != 0) {
            if (p.getYears() == 1) {
                return p.getYears() + " " + c.getString(R.string.date_year)
                        + " " + c.getString(R.string.date_suffix_ago);
            } else {
                return p.getYears() + " " + c.getString(R.string.date_years)
                        + " " + c.getString(R.string.date_suffix_ago);
            }
        }

        if (p.getMonths() != 0) {
            if (p.getMonths() == 1) {
                return p.getMonths() + " " + c.getString(R.string.date_month)
                        + " " + c.getString(R.string.date_suffix_ago);
            } else {
                return p.getMonths() + " " + c.getString(R.string.date_months)
                        + " " + c.getString(R.string.date_suffix_ago);
            }
        }

        if (p.getWeeks() != 0) {
            if (p.getWeeks() == 1) {
                return p.getWeeks() + " " + c.getString(R.string.date_week)
                        + " " + c.getString(R.string.date_suffix_ago);
            } else {
                return p.getWeeks() + " " + c.getString(R.string.date_week)
                        + " " + c.getString(R.string.date_suffix_ago);
            }
        }

        if (p.getDays() != 0) {
            if (p.getDays() == 1) {
                return p.getDays() + " " + c.getString(R.string.date_day)
                        + " " + c.getString(R.string.date_suffix_ago);
            } else {
                return p.getDays() + " " + c.getString(R.string.date_days)
                        + " " + c.getString(R.string.date_suffix_ago);
            }
        }

        if (p.getHours() != 0) {
            if (p.getHours() == 1) {
                return p.getHours() + " " + c.getString(R.string.date_hour)
                        + " " + c.getString(R.string.date_suffix_ago);
            } else {
                return p.getHours() + " " + c.getString(R.string.date_hours)
                        + " " + c.getString(R.string.date_suffix_ago);
            }
        }

        if (p.getMinutes() != 0) {
            if (p.getMinutes() == 1) {
                return p.getMinutes() + " " + c.getString(R.string.date_minute)
                        + " " + c.getString(R.string.date_suffix_ago);
            } else {
                return p.getMinutes() + " " + c.getString(R.string.date_minutes)
                        + " " + c.getString(R.string.date_suffix_ago);
            }
        }

        if (p.getMinutes() != 0) {
            if (p.getSeconds() <= 1) {
                return c.getString(R.string.date_just_now);
            } else {
                return p.getSeconds() + " " + c.getString(R.string.date_seconds)
                        + " " + c.getString(R.string.date_suffix_ago);
            }
        }

        Calendar calendar = Calendar.getInstance();
        Calendar.getInstance().setTimeInMillis(dateStar);
        return Commons.dateToString(calendar.getTime(), c);
    }


    public static String timeAgo(Context c, DateTime dateStar) {
        return timeAgo(c, dateStar, new DateTime());
    }

    public static String timeAgo(Context c, DateTime dateStar, DateTime dateEnd) {
        return timeAgo(c, dateStar, dateEnd);
    }

    public static Period diferenceBetewnDate(long dateStar, long dateEnd) {
        Period period = new Period(dateStar, dateEnd);
        //Log.d(tag, "periodDefault " + PeriodFormat.getDefault().print(period));
        return period;
    }

    public static Period diferenceBetewnDateInDay(long dateStar, long dateEnd) {
        Period period = new Period(dateStar, dateEnd, PeriodType.dayTime());
        //Log.d(tag, "periodDefault In Day " + PeriodFormat.getDefault().print(period));
        return period;
    }

    public static Period diferenceBetewnDate(DateTime dateStar, DateTime dateEnd) {
        return diferenceBetewnDate(dateStar.getMillis(), dateEnd.getMillis());
    }

    public static Period diferenceBetewnDateInDay(DateTime dateStar, DateTime dateEnd) {
        return diferenceBetewnDateInDay(dateStar.getMillis(), dateEnd.getMillis());
    }


}
