package controllers;

import play.data.FormFactory;
import play.mvc.Controller;
import services.UserService;

/**
 * @author Tatsuya Oba
 */
public class BaseController extends Controller {
    protected final UserService userService;
    protected final FormFactory formFactory;

    public BaseController(
            final UserService userService,
            final FormFactory formFactory
    ) {
        this.userService = userService;
        this.formFactory = formFactory;
    }
}
