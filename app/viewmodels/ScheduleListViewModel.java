package viewmodels;

import models.Schedule;
import models.Term;
import models.User;
import services.UserService;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class ScheduleListViewModel {
    public final List<ScheduleViewModel> scheduleList;

    public final List<Date> dateList;

    public final List<Term> termList;

    public ScheduleListViewModel(
            final List<ScheduleViewModel> scheduleList,
            final List<Date> dateList,
            final List<Term> termList
    ) {
        this.scheduleList = scheduleList;
        this.dateList = dateList;
        this.termList = termList;
    }

    public static ScheduleListViewModel createFromUserList(
            final List<User> userList,
            final Date from,
            final int days,
            final UserService userService
    ) {
        final List<Term> termList = userService.getAllTerm();
        final List<Date> dateList = DateUtils.createDateList(from, days);
        final List<ScheduleViewModel> scheduleViewList = userList.stream()
                .map(user -> {
                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(from);
                    calendar.add(Calendar.DAY_OF_YEAR, days);
                    final Date endDate = calendar.getTime();
                    final List<Schedule> scheduleList = userService.getScheduleList(
                            user,
                            from,
                            endDate
                    );
                    final List<Boolean> freeList = createFreeList(
                            scheduleList,
                            dateList,
                            termList
                    );
                    return new ScheduleViewModel(
                            user,
                            freeList
                    );
                }).collect(Collectors.toList());

        return new ScheduleListViewModel(
                scheduleViewList,
                dateList,
                termList
        );
    }

    private static List<Boolean> createFreeList(
            final List<Schedule> scheduleList,
            final List<Date> dateList,
            final List<Term> termList
    ) {
        final List<Boolean> freeList = new ArrayList<>();
        dateList.forEach(date -> {
            final List<Schedule> filteredDate = scheduleList.stream()
                    .filter(schedule -> schedule.date.equals(date))
                    .collect(Collectors.toList());
            termList.forEach(term -> {
                final List<Schedule> filteredTerm = filteredDate.stream()
                        .filter(schedule -> schedule.term.equals(term))
                        .collect(Collectors.toList());
                freeList.add(filteredTerm.size() > 0 ? filteredTerm.get(0).free : null);
            });
        });
        return freeList;
    }
}
