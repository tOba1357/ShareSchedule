package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Tatsuya Oba
 */
@Entity
@Table(name = "tblschedule")
public class Schedule extends Model {
    @Id
    public Integer id;

    @ManyToOne()
    @JoinColumn(name = "id")
    public User user;

    public Date date;

    @ManyToOne()
    @JoinColumn(name = "id")
    public Term term;

    public Boolean free;
}
