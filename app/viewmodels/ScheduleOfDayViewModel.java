package viewmodels;

import models.Schedule;
import models.Term;
import models.User;
import services.UserService;
import utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class ScheduleOfDayViewModel {
    public final Date date;
    public final List<Schedule> scheduleList;

    public ScheduleOfDayViewModel(
            final Date date,
            final List<Schedule> scheduleList
    ) {
        this.date = date;
        this.scheduleList = scheduleList;
    }

    public static List<ScheduleOfDayViewModel> create(
            final User user,
            final Date from,
            final int days,
            final UserService userService
    ) {
        final List<Schedule> scheduleList = userService.getScheduleList(user, from, DateUtils.addDay(from, days));
        final List<Term> termList = userService.getAllTerm();
        DateUtils.createDateList(from, days).stream()
                .map(date -> {
                    final List<Schedule> filteredDate = scheduleList.stream()
                            .filter(schedule -> schedule.date.equals(date))
                            .collect(Collectors.toList());

                })

    }
}
