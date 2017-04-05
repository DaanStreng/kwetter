package DAO;

import DAO.Implementations.*;
import Models.*;
import Utils.DatabaseCleaner;
import org.junit.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Daan on 28-Mar-17.
 */
public class UserDAOTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SimulationTestPU");
    private EntityManager em;
    private EntityTransaction transaction;
    private UserDAO userDAO;
    private ApiKeyDAO keyDAO;
    private ArrayList<User> users = new ArrayList<User>(){{
        add(new User("daan","daanisgek"));
    }};
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
        keyDAO = new ApiKeyDAO();
        keyDAO.setEm(em);

        transaction.begin();
        for (User p : users) {
            userDAO.create(p);
        }
        transaction.commit();
    }

    @Test
    public void loginTest(){
        for(User user:users){
            User request = userDAO.loginUser(user.getUsername(),user.getPassword()+"ff");
            Assert.assertNull(request);
            request = userDAO.loginUser(user.getUsername(),user.getPassword());
            Assert.assertNotNull(request);
            ApiKey key = request.generateKey();
            transaction.begin();
            keyDAO.create(key);
            transaction.commit();
            request = userDAO.checkApiKey(key.getKeystring());
            Assert.assertNotNull(request);
            request = userDAO.checkApiKey(key.getKeystring()+"ff");
            Assert.assertNull(request);
        }
    }
}
