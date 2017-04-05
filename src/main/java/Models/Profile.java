package Models;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.*;

/**
 * Created by Daan on 21-Mar-17.
 */
@NamedQueries({
@NamedQuery(name="getProfileByName",query=
"SELECT p FROM Profile p WHERE p.displayName = :displayname"
),
        @NamedQuery(name="findProfileByName",query=
                "SELECT p FROM Profile p WHERE p.displayName LIKE :displayname"
        ),
@NamedQuery(name="getProfileById",query=
        "SELECT p FROM Profile p WHERE p.id = :id"
)
})
@Entity
@XmlRootElement

public class Profile extends Model{

     @Column(nullable = false)
     private String displayName;
     @Column
     private String name;
     @Column
     private String surname;

     @Lob
     @JsonIgnore
     private Byte[] image;

     private String bio;
     private String location;
     private String website;


     @OneToOne(mappedBy = "profile")
     private User user;

     @JsonIgnore
    public List<Profile> getFollows() {
        return follows;
    }

    public void setFollows(List<Profile> follows) {
        this.follows = follows;
    }

    @JsonIgnore
    @ManyToMany
     private List<Profile> follows;
     public void followProfile(Profile profile){
         if (follows == null)
             follows = new ArrayList<Profile>();
         follows.add(profile);
     }

    @JsonIgnore
     @ManyToMany(mappedBy = "follows")
     private List<Profile> followedBy;
    public void addFollowedBy(Profile profile){
        if (followedBy == null)
            followedBy = new ArrayList<Profile>();
        followedBy.add(profile);
    }
    @JsonIgnore
    public List<Profile> getFollowedBy(){
        return followedBy;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private List<Tweet> sendTweets;

    @JsonIgnore
    @ManyToMany
    private List<Tweet> likes;


    @ManyToMany(mappedBy = "mentions")
    private List<Tweet> mentionedIn;

    public Profile(){

    }
    public Profile(User user){
        this.user = user;
        user.setProfile(this);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
