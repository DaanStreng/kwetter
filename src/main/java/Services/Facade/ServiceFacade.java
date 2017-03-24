package Services.Facade;

import DAO.IKwetterDAO;

import java.util.List;

public abstract class ServiceFacade<T> implements IService<T>{
    public List<T> getAll(){
        return getDAO().getAll();
    }
    public T findById(long id){
        return (T)getDAO().findById(id);
    }
    public void create(T entity){
        getDAO().create(entity);
    }
    public void edit(T entity){
        getDAO().edit(entity);
    }
    public void remove(T entity){
        getDAO().remove(entity);
    }
    protected abstract IKwetterDAO getDAO();
}
