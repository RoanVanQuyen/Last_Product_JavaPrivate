package dataAccess.IntefaceDAO;

public interface DAO <T>{
    boolean insert(T t)  ;
    boolean update(T t) ;
    boolean delete(T t) ;
    T findById(String t) ;
}
