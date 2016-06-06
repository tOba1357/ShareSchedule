package viewmodels;

import models.Schedule;
import models.Term;
import models.User;
import services.UserService;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class ScheduleOfDayViewModel {
    public final User user;
    public final LocalDate date;
    public final List<Schedule> scheduleList;

    public ScheduleOfDayViewModel(
            final User user,
            final LocalDate date,
            final List<Schedule> scheduleList
    ) {
        this.user = user;
        this.date = date;
        this.scheduleList = scheduleList;
    }

    public static List<ScheduleOfDayViewModel> create(
            final User user,
            final LocalDate from,
            final int days,
            final UserService userService
    ) {
        final List<Term> termList = userService.getAllTerm();
        return DateUtils.createDateList(from, days).stream()
                .map(date ->
                    ScheduleOfDayViewModel.create(
                            user,
                            date,
                            termList,
                            userService
                    )
                ).collect(Collectors.toList());
    }

    public static ScheduleOfDayViewModel create(
            final User user,
            final LocalDate date,
            final List<Term> termList,
            final UserService userService
    ) {
        final List<Schedule> scheduleList = userService.getScheduleList(user, date);
        return new ScheduleOfDayViewModel(
                user,
                date,
                termList.stream()
                .map(term -> {
                    final List<Schedule> filteredList = scheduleList.stream()
                            .filter(schedule -> term.equals(schedule.term))
                            .collect(Collectors.toList());
                    return filteredList.size() > 0 ? filteredList.get(0) : createEmptySchedule(date, term);
                }).collect(Collectors.toList())
        );
    }


    private static Schedule createEmptySchedule(
            final LocalDate date,
            final Term term
    ) {
        final Schedule schedule = new Schedule();
        schedule.date = date;
        schedule.term = term;
        return  schedule;
    }
}
