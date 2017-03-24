package Services;

import DAO.IKwetterDAO;
import DAO.Implementations.UserDAO;
import DAO.Interfaces.IUserDAO;
import Models.ApiKey;
import Models.Subject;
import Models.User;
import Services.Facade.ServiceFacade;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Daan on 21-Mar-17.
 */
@Stateless
public class SubjectService extends ServiceFacade<Subject> {

    @Inject
    IUserDAO dao;

    protected IKwetterDAO getDAO() {
        return dao;
    }

}
