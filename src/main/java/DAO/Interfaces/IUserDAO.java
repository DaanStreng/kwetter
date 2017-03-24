package DAO.Interfaces;

import DAO.IKwetterDAO;
import Models.User;

import javax.persistence.NamedQuery;

/**
 * Created by Daan on 21-Mar-17.
 */
public interface IUserDAO extends IKwetterDAO<User> {

    public User loginUser(String username, String password);
    public User checkApiKey(String apikey);
}
