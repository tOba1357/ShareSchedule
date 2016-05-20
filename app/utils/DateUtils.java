package utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author Tatsuya Oba
 */
public class DateUtils {
    public static String getDateOfWeek(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY: return "日";
            case Calendar.MONDAY: return "月";
            case Calendar.TUESDAY: return "火";
            case Calendar.WEDNESDAY: return "水";
            case Calendar.THURSDAY: return "木";
            case Calendar.FRIDAY: return "金";
            case Calendar.SATURDAY: return "土";
        }
        throw new IllegalStateException();
    }

    public static List<Date> createDateList(
            final Date from,
            final int days
    ) {
        final List<Date> dateList = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        for (int i = 0; i < days; i++) {
            final Date date = calendar.getTime();
            dateList.add(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dateList;
    }

    public static Date addDay(final Date date, final int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }
}
