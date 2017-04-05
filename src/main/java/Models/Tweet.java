package Models;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Daan on 21-Mar-17.
 */
@NamedQueries({
        @NamedQuery(name = "getTweetsBySubject",
                query = " SELECT t FROM Tweet t WHERE t.message LIKE :subject "),
        @NamedQuery(name = "getTweetsByProfileId", query = "SELECT t FROM Tweet t where t.createdBy = :id")
})
@Entity
@XmlRootElement
public class Tweet extends Model {

    public Profile getCreatedBy() {
        return createdBy;
    }

    @ManyToOne
    private Profile createdBy;

    public Date getTimestamp() {
        return timestamp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public Tweet(){};
    public Tweet(Profile createdBy,Date timestamp, String message){
        this.createdBy = createdBy;
        this.timestamp = timestamp;
        this.message = message;
    }
}
