package Controllers;

import Models.User;
import Services.UserService;

/**
 * Created by Daan on 22-Mar-17.
 */
public class Controller {
    public User checkBegin(UserService userService,String apiKey){
        User user = userService.getUserDAO().checkApiKey(apiKey);
        return user;
    }
}
