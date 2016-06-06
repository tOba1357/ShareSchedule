package controllers;

import action.response.StandardResponse;
import com.google.inject.Inject;
import models.User;
import org.apache.commons.lang3.StringUtils;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Result;
import services.UserService;

/**
 * @author Tatsuya Oba
 */
public class UsersController extends BaseController {
    @Inject
    public UsersController(
            final UserService userService,
            final FormFactory formFactory
    ) {
        super(userService, formFactory);
    }

    public Result showUserList() {
        return ok(views.html.userlist.apply(userService.getAllUser()));
    }

    public Result createUser() {
        final DynamicForm form = formFactory.form()
                .bindFromRequest();
        final User user = new User();
        user.name = form.get("name");

        if (StringUtils.isBlank(form.get("name"))) {
            return badRequest("名前を入力してねー。");
        }
        final StandardResponse response = userService.save(user);
        if (response.isSuccess()) {
            return redirect(routes.UsersController.showUserList());
        }
        return badRequest(response.getMessage());
    }

    public Result deleteUser(final Integer id) {
        if (id == null) {
            return ok(routes.MainController.home().path());
        }

        final User user = userService.getUserById(id);
        if (user == null) {
            return ok(routes.MainController.home().path());
        }
        userService.delete(user);

        return ok(routes.UsersController.showUserList().path());
    }
}
