package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Tatsuya Oba
 */
public class DateUtils {
    public static String getDateOfWeek(final LocalDate date) {
        switch (date.getDayOfWeek()) {
            case SUNDAY:
                return "日";
            case MONDAY:
                return "月";
            case TUESDAY:
                return "火";
            case WEDNESDAY:
                return "水";
            case THURSDAY:
                return "木";
            case FRIDAY:
                return "金";
            case SATURDAY:
                return "土";
        }
        throw new IllegalStateException();
    }

    public static List<LocalDate> createDateList(
            final LocalDate from,
            final int days
    ) {
        final List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            dateList.add(from.plusDays(i));
        }
        return dateList;
    }

    public static String getSimpleString(final LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM/dd")) + " "
                + "(" + getDateOfWeek(date) + ")";
    }
}
