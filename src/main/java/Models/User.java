package Models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by Daan on 21-Mar-17.
 */
@Entity
@XmlRootElement
@NamedQuery(name = "getByUsernamePassword"
        , query="SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
@NamedQueries({
        @NamedQuery(
                name="User.getUser",
                query = "SELECT u FROM User u WHERE " +
                        "u.username = :username  AND " +
                        "u.password = :password"
        ),
        @NamedQuery(
                name="getAll",
                query="SELECT u FROM User u"
        ),
        @NamedQuery(
            name="getByApiKey",
                query = "SELECT u FROM User u JOIN ApiKey ak WHERE ak.keystring = :apikey AND ak.user.id = u.id"
        )
})
public class User extends Model {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<ApiKey> keys;

    @Column
    private int securityElevation = 5;

    @Enumerated(EnumType.STRING)
    private UserLanguage language;

    @OneToOne
    private Profile profile;


    public ApiKey generateKey(){
        if (keys == null){
            keys = new ArrayList<ApiKey>();
        }
        ApiKey key = new ApiKey();
        this.keys.add(key);
        key.setUser(this);
        return key;
    }
    public ApiKey removeKey(String key){
        ApiKey ky = null;
        if (keys !=null){
            for(ApiKey ak : keys){
                if (ak.getKeystring() == key){
                    ky = ak;
                    break;
                }
            }
        }
        if (ky!=null)
            this.keys.remove(ky);
        return ky;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSecurityElevation() {
        return securityElevation;
    }

    public void setSecurityElevation(int securityElevation) {
        this.securityElevation = securityElevation;
    }

    public UserLanguage getLanguage() {
        return language;
    }

    public void setLanguage(UserLanguage language) {
        this.language = language;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }
    public Profile getProfile(){return profile;}
}
