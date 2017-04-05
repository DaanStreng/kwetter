package DAO;

import DAO.Implementations.ApiKeyDAO;
import DAO.Implementations.ProfileDAO;
import DAO.Implementations.TweetDAO;
import DAO.Implementations.UserDAO;
import Models.Profile;
import Models.Tweet;
import Models.User;
import Utils.DatabaseCleaner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daan on 28-Mar-17.
 */
public class TweetDAOTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SimulationTestPU");
    private EntityManager em;
    private EntityTransaction transaction;
    private UserDAO userDAO;
    private TweetDAO tweetDAO;
    private ProfileDAO profileDAO;

    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();


    @Before
    public void before() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
        em = emf.createEntityManager();
        transaction = em.getTransaction();
        userDAO = new UserDAO();
        userDAO.setEm(em);
        tweetDAO = new TweetDAO();
        tweetDAO.setEm(em);
        profileDAO = new ProfileDAO();
        profileDAO.setEm(em);

        transaction.begin();
        User daan = new User("daan", "daanisgek");
        userDAO.create(daan);
        Profile dprof = new Profile(daan);
        dprof.setDisplayName("daan");
        profileDAO.create(dprof);
        transaction.commit();
        tweets.add(new Tweet(dprof,new Date(),"Hallo #hallo @daan jo"));
        transaction.begin();
        for(Tweet tweet:tweets)
            tweetDAO.create(tweet);
        transaction.commit();
    }

    @Test
    public void testGetTweetsBySubject(){
        List<Tweet> tw = tweetDAO.getTweetsBySubject("hallo");
        Assert.assertEquals(tw.size(),1);
    }
}
