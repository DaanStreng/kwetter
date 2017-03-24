package DAO.Implementations;

import DAO.DaoFacade;
import DAO.Interfaces.IApiKeyDAO;
import DAO.Interfaces.IProfileDAO;
import Models.ApiKey;
import Models.Profile;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Daan on 22-Mar-17.
 */
@Stateless
public class ApiKeyDAO extends DaoFacade<ApiKey> implements IApiKeyDAO {

    @PersistenceContext(name = "SimulationPU")
    EntityManager em;

    public ApiKeyDAO() {
        super(ApiKey.class);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
