package DAO.Implementations;

import DAO.DaoFacade;
import DAO.Interfaces.IUserDAO;
import Models.User;

import javax.ejb.Stateless;
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

    public List<User> getAllUsers(){
        TypedQuery<User> query =
                em.createNamedQuery("getAll",User.class);
        List<User> results = query.getResultList();
        return results;
    }
    public User loginUser(String username, String password){
        TypedQuery<User> query =
                em.createNamedQuery("getByUsernamePassword",User.class);
        query.setParameter("username",username);
        query.setParameter("password",password);
        return query.getSingleResult();
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

}
