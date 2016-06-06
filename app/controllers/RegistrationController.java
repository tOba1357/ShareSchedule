package controllers;

import com.google.inject.Inject;
import models.Schedule;
import models.State;
import models.Term;
import models.User;
import org.apache.commons.lang3.StringUtils;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Result;
import services.UserService;
import viewmodels.ScheduleOfDayViewModel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class RegistrationController extends BaseController {
    @Inject
    public RegistrationController(
            final UserService userService,
            final FormFactory formFactory
    ) {
        super(userService, formFactory);
    }

    public Result showRegistrationForm(final Integer id) {
        final User user = userService.getUserById(id);
        final LocalDate from = LocalDate.now().plusDays(1);
        final List<ScheduleOfDayViewModel> scheduleOfDayViewModelList = ScheduleOfDayViewModel.create(
                user,
                from,
                7,
                userService
        );
        return ok(views.html.registration.apply(user, scheduleOfDayViewModelList));
    }

    public Result registration(final Integer id) {
        final User user = userService.getUserById(id);
        final List<Term> termList = userService.getAllTerm();
        final List<Schedule> scheduleList = createScheduleListFromForm(
                formFactory.form().bindFromRequest(),
                user,
                termList
        );
        scheduleList.forEach(userService::saveOrUpdate);
        return redirect(routes.RegistrationController.showRegistrationForm(id));
    }

    private List<Schedule> createScheduleListFromForm(
            final DynamicForm form,
            final User user,
            final List<Term> termList
    ) {
        final LocalDate date = LocalDate.parse(form.get("date"));
        return termList.stream()
                .map(term -> {
                    final String value = form.get(term.id.toString());
                    final Schedule schedule = new Schedule();
                    if (StringUtils.isNotBlank(form.get("schedule_id_" + term.id))) {
                        schedule.id = Integer.valueOf(form.get("schedule_id_" + term.id));
                    }
                    schedule.user = user;
                    schedule.date = date;
                    schedule.term = term;
                    if (String.valueOf(State.FREE.ordinal()).equals(value)) {
                        schedule.state = State.FREE;
                    } else if (String.valueOf(State.BUSY.ordinal()).equals(value)){
                        schedule.state = State.BUSY;
                    } else {
                        schedule.state = State.UNDECIDED;
                    }
                    return schedule;
                })
                .collect(Collectors.toList());
    }
}
