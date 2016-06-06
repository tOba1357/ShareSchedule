package controllers;

import com.google.inject.Inject;
import models.Term;
import org.apache.commons.lang3.StringUtils;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Result;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
public class SettingController extends BaseController {
    @Inject
    public SettingController(
            final UserService userService,
            final FormFactory formFactory
    ) {
        super(userService, formFactory);
    }

    public Result settingForm() {
        return ok(views.html.settings.apply());
    }

    public Result setTerm() {
        final DynamicForm form = formFactory.form()
                .bindFromRequest();
        final List<Term> termList = createTermListFromForm(form);
        if (termList.size() == 0) {
            return badRequest("一つ以上入れてね。");
        }
        userService.deleteAllTerm();
        termList.forEach(userService::saveTerm);
        return redirect(routes.MainController.home());
    }

    private List<Term> createTermListFromForm(final DynamicForm form) {
        final List<Term> termList = new ArrayList<>();
        for (int i = 0; ; i++) {
            final String termString = form.get(String.valueOf(i));
            if (termString == null) {
                break;
            }
            if (StringUtils.isBlank(termString)) {
                continue;
            }
            final Term term = new Term();
            term.description = termString;
            termList.add(term);
        }
        return termList;
    }

}
