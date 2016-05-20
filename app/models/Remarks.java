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
@Table(name = "tblremarks")
public class Remarks extends Model {
    @Id
    public Integer id;

    public String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    public User user;

    public Date date;
}
