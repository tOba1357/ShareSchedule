package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Tatsuya Oba
 */
@Entity
@Table(name = "tblterm")
public class Term {
    @Id
    public Integer id;

    public String description;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    public List<Schedule> scheduleList;


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof  Term)) {
            return false;
        }
        return this.description.equals(((Term) obj).description);
    }
}
