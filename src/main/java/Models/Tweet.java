package Models;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Daan on 21-Mar-17.
 */
@NamedQuery(name="getTweetsBySubject",
query=" SELECT t FROM Tweet t WHERE t.message LIKE :subject ")
@Entity
@XmlRootElement
public class Tweet extends Model {

    @ManyToOne
    private Profile createdBy;
    private Date timestamp;
    private String message;

    @ManyToMany(mappedBy = "likes")
    private List<Profile> likedBy;

    @ManyToMany
    private List<Profile> mentions;

    @ManyToMany
    private List<Subject> subjects;

    public List<Subject> getSubjects(){
        return subjects;
    }
}
