package Bean;

import Models.ApiKey;
import Models.Profile;
import Models.User;
import Services.ApiKeyService;
import Services.ProfileService;
import Services.TweetService;
import Services.UserService;
import com.sun.deploy.net.HttpRequest;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Daan on 29-Mar-17.
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {
    @Inject
    UserService userService;
    @Inject
    ApiKeyService keyService;
    @Inject
    ProfileService profileService;

    @Inject
    TweetService tweetService;

    private String username;
    private String password;
    private String loginResult="";
    private String saveMessage = "";

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private Profile profile;



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public UserBean() {
    }

    public String login() {
        user = userService.loginUser(username, password);
        this.profile = user.getProfile();
        if (user!=null) {
            loginResult = "login success";
            if (user.getKeys().size()<=0) {
                ApiKey key = user.generateKey();
                keyService.create(key);
            }
        }
        else
        {
            loginResult = "login failed";
            return "login.xhtml";
        }
        return "home.xhtml";
    }



    public String getSaveMessage() {
        return saveMessage;
    }

    public void saveProfile(){
        profileService.edit(this.getProfile());
        saveMessage = "profile saved";
    }
    public void register(){
        User user = userService.createUser(username,password);
        this.user = user;
        loginResult="register successfull pleas log in";

    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername(){
       return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getLoginResult() {
        return loginResult;
    }
}
