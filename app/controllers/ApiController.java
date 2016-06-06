package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Schedule;
import models.State;
import models.Term;
import models.User;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import services.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
public class ApiController extends BaseController {
    @Inject
    public ApiController(
            final UserService userService,
            final FormFactory formFactory
    ) {
        super(userService, formFactory);
    }

    public Result getTerms() {
        final List<Term> termList = userService.getAllTerm();
        return ok(Json.toJson(termList));
    }

    public Result getStates() {
        return ok(Json.toJson(State.toJson()));
    }

    public Result getSchedules(final Integer userId, final String dateString) {
        try {
            final LocalDate date = LocalDate.parse(dateString);
            final List<Schedule> scheduleList = userService.getScheduleList(userId, date);
            return ok(Json.toJson(scheduleList));
        } catch (DateTimeParseException e) {
            return badRequest();
        }
    }

    public Result setSchedulesOfDay(final Integer userId, final String dateString) {
        final User user = userService.getUserById(userId);
        if (user == null) {
            return badRequest();
        }
        try {
            final LocalDate date = LocalDate.parse(dateString);
            final List<Schedule> scheduleList = createSchedulesOfDayFromJson(
                    user,
                    date,
                    request().body().asJson(),
                    userService.getAllTerm()
            );
            scheduleList.forEach(userService::saveOrUpdate);
            return ok();
        } catch (DateTimeParseException e) {
            return badRequest();
        }
    }

    public Result setSchedules(final Integer userId) {
        final User user = userService.getUserById(userId);
        if (user == null) {
            return badRequest();
        }
        final List<Schedule> scheduleList = createSchedulesFromJson(
                user,
                request().body().asJson(),
                userService.getAllTerm()
        );
        scheduleList.forEach(userService::saveOrUpdate);
        return ok(routes.MainController.home().path());
    }

    private List<Schedule> createSchedulesFromJson(
            final User user,
            final JsonNode json,
            final List<Term> termList
    ) {
        final List<Schedule> scheduleList = new ArrayList<>();
        json.forEach(scheduleOfDayJson -> {
            try {
                final LocalDate date = LocalDate.parse(scheduleOfDayJson.get("date").asText());
                scheduleList.addAll(createSchedulesOfDayFromJson(
                        user,
                        date,
                        scheduleOfDayJson,
                        termList
                ));
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        });
        return scheduleList;
    }


    private List<Schedule> createSchedulesOfDayFromJson(
            final User user,
            final LocalDate date,
            final JsonNode json,
            final List<Term> termList
    ) {
        final List<Schedule> scheduleList = new ArrayList<>();
        json.get("schedule").forEach(scheduleJson -> {
            final Schedule schedule = new Schedule();
            if (scheduleJson.get("id") != null) {
                schedule.id = scheduleJson.get("id").asInt();
            }
            schedule.user = user;
            schedule.date = date;
            schedule.state = State.fromInt(scheduleJson.get("state").asInt());
            schedule.term = termList.stream().filter(term -> term.id.equals(scheduleJson.get("term").asInt()))
                    .findFirst().orElse(null);
            if (schedule.term == null) {
                return;
            }
            scheduleList.add(schedule);
        });
        return scheduleList;
    }

}
