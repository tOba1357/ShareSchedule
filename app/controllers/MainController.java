package controllers;

import com.google.inject.Inject;
import models.User;
import play.data.FormFactory;
import play.mvc.Result;
import services.UserService;
import viewmodels.ScheduleListViewModel;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
public class MainController extends BaseController {
    @Inject
    public MainController(
            final UserService userService,
            final FormFactory formFactory
    ) {
        super(userService, formFactory);
    }

    public Result home() {
        final List<User> userList = userService.getAllUser();
        final List<ScheduleListViewModel> scheduleListViewModel = ScheduleListViewModel.createFromUserList(
                userList,
                LocalDate.now(),
                7,
                userService
        );
        return ok(views.html.home.apply(scheduleListViewModel));
    }
}
