package carsharing;

import java.util.List;
import java.util.Optional;

public class CustomerDaoJdbc implements CustomerDao<Customer> {


    @Override
    public Optional<Customer> get(Customer customer) {
        return Optional.empty();
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void save(Customer customer) {

    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }
}
