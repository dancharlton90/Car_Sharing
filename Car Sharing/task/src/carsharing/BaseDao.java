package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    Optional<T> get(T t);
    Optional<T> getById(int id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(T t);

}
