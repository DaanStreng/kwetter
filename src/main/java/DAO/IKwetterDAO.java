package DAO;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Wouter Vanmulken on 14-3-2017.
 */
public interface IKwetterDAO<T> {
    EntityManager getEntityManager();
    List<T> getAll();
    T findById(long id);
    void create(T entity);
    void edit(T entity);
    void remove(T entity);
}
