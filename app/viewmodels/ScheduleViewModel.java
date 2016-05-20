package viewmodels;

import models.User;

import java.util.List;

/**
 * @author Tatsuya Oba
 */
public class ScheduleViewModel {
    public final User user;

    public final List<Boolean> freeList;

    public ScheduleViewModel(
            final User user,
            final List<Boolean> freeList
    ) {
        this.user = user;
        this.freeList = freeList;
    }
}
