package Services.Facade;

import java.util.List;

/**
 * Created by Wouter Vanmulken on 14-3-2017.
 */
public interface IService<T> {
    List<T> getAll();
    T findById(long id);
    void create(T entity);
    void edit(T entity);
    void remove(T entity);
}

