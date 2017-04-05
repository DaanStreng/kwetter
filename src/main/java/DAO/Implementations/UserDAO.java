package DAO.Implementations;

import DAO.DaoFacade;
import DAO.Interfaces.IUserDAO;
import Models.Profile;
import Models.User;
import Services.ProfileService;
import Services.UserService;
import Utils.StringUtils;
import org.junit.Test;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Daan on 21-Mar-17.
 */



@Stateless
public class UserDAO extends DaoFacade<User> implements IUserDAO {

    @PersistenceContext(name = "SimulationPU")
    EntityManager em;



    public UserDAO() {
        super(User.class);
    }

    public EntityManager getEntityManager() {
        return em;
    }
    public void setEm(EntityManager em) {
        this.em = em;
    }
    public List<User> getAllUsers(){
        TypedQuery<User> query =
                em.createNamedQuery("getAll",User.class);
        List<User> results = query.getResultList();
        return results;
    }
    public User loginUser(String username, String password){
        password = StringUtils.Hash(password);
        TypedQuery<User> query =
                em.createNamedQuery("getByUsernamePassword",User.class);
        query.setParameter("username",username);
        query.setParameter("password",password);
        try {
            return query.getSingleResult();
        }
        catch(NoResultException ex){
            return null;
        }
    }
    public User checkApiKey(String apikey){
        TypedQuery<User> query =
                em.createNamedQuery("getByApiKey",User.class);
        query.setParameter("apikey",apikey);
        try {
            return query.getSingleResult();
        }
        catch(Exception ex){return null;}
    }
    public User createUser(String username, String password){
        User user = new User(username,password);
        this.create(user);
        return user;

    }


}
