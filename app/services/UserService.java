package services;

import action.response.StandardResponse;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Expr;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.Schedule;
import models.Term;
import models.User;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
@Singleton
public class UserService {
    private final EbeanServer ebeanServer;

    @Inject
    public UserService(final EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }

    public List<User> getAllUser() {
        return ebeanServer.find(User.class)
                .findList();
    }

    public User getUserById(final Integer id) {
        if (id == null) {
            return null;
        }
        return ebeanServer.find(User.class)
                .setId(id)
                .findUnique();
    }

    public User getUserByName(final String name) {
        return ebeanServer.find(User.class)
                .where()
                .eq("name", name)
                .findUnique();
    }

    public StandardResponse save(final User user) {
        final boolean exist = ebeanServer.find(User.class)
                .where()
                .eq("name", user.name)
                .findRowCount() > 0;
        if (exist) {
            return new StandardResponse(
                    false,
                    "ユーザ名が重複しています。"
            );
        }
        ebeanServer.save(user);
        return StandardResponse.SUCCESS;
    }

    public void delete(final User user) {
        ebeanServer.delete(user);
    }

    public StandardResponse saveOrUpdate(final Schedule schedule) {
        if (schedule.id == null) {
            ebeanServer.save(schedule);
        } else {
            ebeanServer.update(schedule);
        }
        return StandardResponse.SUCCESS;
    }

    public List<Schedule> getScheduleList(
            final User user,
            final LocalDate date
    ) {
        return getScheduleList(user.id, date);
    }

    public List<Schedule> getScheduleList(
            final Integer userId,
            final LocalDate date
    ) {
        return ebeanServer.find(Schedule.class)
                .where()
                .and(
                        Expr.eq("user_id", userId),
                        Expr.eq("date", date)
                )
                .findList();
    }


    public List<Term> getAllTerm() {
        return ebeanServer.find(Term.class)
                .orderBy("id")
                .findList();
    }

    public void deleteAllTerm() {
        getAllTerm().forEach(ebeanServer::delete);
    }

    public void saveTerm(final Term term) {
        ebeanServer.save(term);
    }
}
