package Services;

import DAO.IKwetterDAO;
import DAO.Implementations.UserDAO;
import DAO.Interfaces.IProfileDAO;
import DAO.Interfaces.IUserDAO;
import Models.Profile;
import Models.User;
import Services.Facade.ServiceFacade;
import Utils.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Daan on 21-Mar-17.
 */
@Stateless
public class UserService extends ServiceFacade<User> {

    @Inject
    IUserDAO dao;

    @Inject
    IProfileDAO profileDAO;


    protected IKwetterDAO getDAO() {
        return dao;
    }

    public IUserDAO getUserDAO(){
        return (IUserDAO) getDAO();
    }

    public User createUser(String username, String password){
        password = StringUtils.Hash(password);
        User user = dao.createUser(username,password);
        Profile profile = new Profile();
        profile.setDisplayName(username);
        user.setProfile(profile);
        profileDAO.create(profile);
        dao.edit(user);
        return user;
    }
    public User loginUser(String username, String password){
        return dao.loginUser(username,password);
    }
    public User checkApiKey(String keyString){
        return dao.checkApiKey(keyString);
    }
}
