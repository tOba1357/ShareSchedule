package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * @author Tatsuya Oba
 */
@Entity
@Table(name = "tblschedule")
public class Schedule extends Model {
    @Id
    public Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public LocalDate date;

    @ManyToOne
    @JoinColumn(name = "term_id")
    public Term term;

    public State state;
}
