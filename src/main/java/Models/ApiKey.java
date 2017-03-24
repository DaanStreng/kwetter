package Models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

/**
 * Created by Daan on 22-Mar-17.
 */
@Entity
@XmlRootElement
public class ApiKey extends Model {

    private String keystring;

    @ManyToOne
    private User user;

    public ApiKey() {
        generate();
    }
    private void generate(){
        keystring = UUID.randomUUID().toString();
    }
    public void setUser(User user){
        this.user = user;
    }
    public String getKeystring(){
        return keystring;
    }
}
