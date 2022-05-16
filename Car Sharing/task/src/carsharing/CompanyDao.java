package carsharing;

import java.util.List;
import java.util.Optional;

public interface CompanyDao<T> extends BaseDao<T> {

    List<Car> getCompanyCars(Company aCompany);

    List<Car> getAllAvailableCars(Company company);

    @Override
    Optional<T> getById(int id);
}
