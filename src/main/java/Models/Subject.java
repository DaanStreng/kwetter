package Models;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Daan on 21-Mar-17.
 */
@Entity
@XmlRootElement
public class Subject extends Model {

    private String name;

    @ManyToMany(mappedBy = "subjects")
    private List<Tweet> subjectOf;
}
