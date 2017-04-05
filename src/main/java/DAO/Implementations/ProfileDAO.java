package DAO.Implementations;


        import DAO.DaoFacade;
        import DAO.Interfaces.IProfileDAO;
        import Models.Profile;
        import Models.Tweet;

        import javax.ejb.Stateless;
        import javax.persistence.EntityManager;
        import javax.persistence.PersistenceContext;
        import javax.persistence.TypedQuery;
        import java.util.List;

/**
 * Created by Daan on 22-Mar-17.
 */
@Stateless
public class ProfileDAO extends DaoFacade<Profile> implements IProfileDAO {

    @PersistenceContext(name = "SimulationPU")
    EntityManager em;

    public ProfileDAO() {
        super(Profile.class);
    }

    public EntityManager getEntityManager() {
        return em;
    }
    public void setEm(EntityManager em) {
        this.em = em;
    }
    public Profile getProfileByProfileName(String profilename){
        TypedQuery<Profile> query
                = em.createNamedQuery("getProfileByName",Profile.class);

        query.setParameter("displayname",profilename);
        return query.getSingleResult();
    }
    public List<Profile> findProfileByProfileName(String profilename){
        TypedQuery<Profile> query
                = em.createNamedQuery("findProfileByName",Profile.class);

        query.setParameter("displayname","%"+profilename+"%");
        return query.getResultList();
    }
    public Profile getProfileById(Long id){
        TypedQuery<Profile> query
                = em.createNamedQuery("getProfileById",Profile.class);

        query.setParameter("id",id);
        return query.getSingleResult();
    }
}
