package models;

import com.avaje.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
@Entity
@Table(name = "tbluser")
public class User extends Model {
    @Id
    public Integer id;

    public String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Schedule> scheduleList;
}
