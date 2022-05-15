package carsharing;

import java.util.List;

public interface CompanyDao<T> extends BaseDao<T> {

    List<Car> getCompanyCars(Company aCompany);

}
