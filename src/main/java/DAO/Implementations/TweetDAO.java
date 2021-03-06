package DAO.Implementations;

import DAO.DaoFacade;
import DAO.Interfaces.ITweetDAO;
import DAO.Interfaces.IUserDAO;
import Models.Tweet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Daan on 21-Mar-17.
 */
@Stateless
public class TweetDAO extends DaoFacade<Tweet> implements ITweetDAO {

    @PersistenceContext(name = "SimulationPU")
    EntityManager em;

    public TweetDAO() {
        super(Tweet.class);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public List<Tweet> getTweetsBySubject(String subject){
        TypedQuery<Tweet> query
                = em.createNamedQuery("getTweetsBySubject",Tweet.class);
        subject ="%#"+subject+"%";
        query.setParameter("subject",subject);
        return query.getResultList();
    }
    public List<Tweet> getTweetsByMentions(String mentions){
        TypedQuery<Tweet> query
                = em.createNamedQuery("getTweetsBySubject",Tweet.class);
        mentions ="%@"+mentions+"%";
        query.setParameter("subject",mentions);
        return query.getResultList();
    }
}
