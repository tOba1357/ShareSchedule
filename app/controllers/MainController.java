package controllers;

import action.response.StandardResponse;
import com.google.inject.Inject;
import models.Schedule;
import models.Term;
import models.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;
import utils.DateUtils;
import viewmodels.ScheduleListViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tatsuya Oba
 */
public class MainController extends Controller {
    private final UserService userService;
    private final FormFactory formFactory;

    @Inject
    public MainController(
            final UserService userService,
            final FormFactory formFactory
    ) {
        this.userService = userService;
        this.formFactory = formFactory;
    }

    public Result createUser() {
        final DynamicForm form = formFactory.form()
                .bindFromRequest();
        final User user = new User();
        user.name = form.get("name");
        final StandardResponse response = userService.save(user);
        if (response.isSuccess()) {
            return redirect(routes.MainController.userList());
        }
        return badRequest(response.getMessage());
    }

    public Result home() {
        final List<User> userList = userService.getAllUser();
        final ScheduleListViewModel scheduleListViewModel = ScheduleListViewModel.createFromUserList(
                userList,
                new Date(),
                7,
                userService
        );
        return ok(views.html.home.apply(scheduleListViewModel));
    }

    public Result userList() {
        return ok(views.html.userlist.apply(userService.getAllUser()));
    }

    public Result registration(final Integer id) {
        final User user = userService.getUserById(id);
        final Date from = DateUtils.addDay(new Date(), 1);
        final Date end = DateUtils.addDay(from, 7);
        final List<Schedule> scheduleList = userService.getScheduleList(user, from, end);

        return ok(views.html.registration.apply(user));
    }

    private List<Schedule> createScheduleListFromForm(
            final DynamicForm form,
            final User user,
            final List<Term> termList
    ) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = format.parse(form.get("date"));
        return termList.stream()
                .map(term -> {
                    final String value = form.get(term.id.toString());
                    final boolean free = "free".equals(value);
                    final Schedule schedule = new Schedule();
                    schedule.user = user;
                    schedule.date = date;
                    schedule.term = term;
                    schedule.free = free;
                    return schedule;
                })
                .collect(Collectors.toList());
    }

}
