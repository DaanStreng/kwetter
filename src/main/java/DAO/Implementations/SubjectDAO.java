package DAO.Implementations;

import DAO.DaoFacade;
import DAO.Interfaces.ISubjectDAO;
import DAO.Interfaces.IUserDAO;
import Models.Subject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * Created by Daan on 21-Mar-17.
 */
@Stateless
public class SubjectDAO extends DaoFacade<Subject> implements ISubjectDAO {

    @PersistenceContext(name = "SimulationPU")
    EntityManager em;

    public SubjectDAO() {
        super(Subject.class);
    }
    public void setEm(EntityManager em) {
        this.em = em;
    }
    public EntityManager getEntityManager() {
        return em;
    }
}
