package Services;

import DAO.IKwetterDAO;
import DAO.Implementations.UserDAO;
import DAO.Interfaces.IProfileDAO;
import DAO.Interfaces.IUserDAO;
import Models.ApiKey;
import Models.Profile;
import Models.User;
import Services.Facade.ServiceFacade;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Daan on 21-Mar-17.
 */
@Stateless
public class ProfileService extends ServiceFacade<Profile> {

    @Inject
    IProfileDAO dao;

    protected IKwetterDAO getDAO() {
        return dao;
    }

    public IProfileDAO getProfileDAO(){
        return (IProfileDAO) getDAO();
    }
}
