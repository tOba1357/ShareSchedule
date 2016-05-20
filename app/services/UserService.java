package services;

import action.response.StandardResponse;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Expr;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.Schedule;
import models.Term;
import models.User;

import java.util.Date;
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

    public StandardResponse save(final Schedule schedule) {
        ebeanServer.save(schedule);
        return StandardResponse.SUCCESS;
    }

    public List<Schedule> getScheduleList(
            final User user,
            final Date from,
            final Date end
    ) {
        //TODO: add order by
        return ebeanServer.find(Schedule.class)
                .where()
                .disjunction()
                .add(Expr.eq("user.id", user.id))
                .add(Expr.ge("date", from))
                .add(Expr.le("date", end))
                .findList();
    }

    public List<Term> getAllTerm() {
        return ebeanServer.find(Term.class)
                .orderBy("id")
                .findList();
    }

}
