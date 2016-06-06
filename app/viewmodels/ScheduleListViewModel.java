package viewmodels;

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
public class ScheduleListViewModel {
    public final List<ScheduleOfDayViewModel> scheduleOfDayList;

    public final LocalDate date;

    public final List<Term> termList;

    public ScheduleListViewModel(
            final List<ScheduleOfDayViewModel> scheduleOfDayList,
            final LocalDate date,
            final List<Term> termList
    ) {
        this.scheduleOfDayList = scheduleOfDayList;
        this.date = date;
        this.termList = termList;
    }

    public static List<ScheduleListViewModel> createFromUserList(
            final List<User> userList,
            final LocalDate from,
            final int days,
            final UserService userService
    ) {
        final List<Term> termList = userService.getAllTerm();
        final List<LocalDate> dateList = DateUtils.createDateList(from, days);
        return dateList.stream()
                .map(date -> new ScheduleListViewModel(userList.stream()
                        .map(user -> ScheduleOfDayViewModel.create(
                                user,
                                date,
                                termList,
                                userService
                        )).collect(Collectors.toList()),
                        date,
                        termList
                )).collect(Collectors.toList());
    }
}
