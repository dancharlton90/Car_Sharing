package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoJdbc implements CustomerDao<Customer> {

    List<Customer> listOfCustomers;
    Connection conn;

    public CustomerDaoJdbc() {
        this.conn = DatabaseManager.getInstance();
    }

    @Override
    public Optional<Customer> get(Customer customer) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> getById(int anId) {
        Optional<Customer> customer = Optional.empty();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM customer " +
                    " WHERE id = " + anId))
            {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Optional<Car> car = new CarDaoJdbc().getById(resultSet.getInt("rented_car_id"));
                    customer = Optional.of(new Customer(id, name, car));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        listOfCustomers = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet customers = statement.executeQuery("SELECT * FROM customer")) {
                while (customers.next()) {
                    String name = customers.getString("name");
                    int id = customers.getInt("id");
                    Optional<Car> rentedCar = new CarDaoJdbc().getById(customers.getInt("rented_car_id"));
                    listOfCustomers.add(new Customer(id, name, Optional.ofNullable(rentedCar.orElse(null))));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfCustomers;
    }

    @Override
    public void save(Customer customer) {
        String createSQL = "INSERT INTO \"CUSTOMER\" " +
                "(name) VALUES (?)";
        try (PreparedStatement createCompany = conn.prepareStatement(createSQL)) {
            createCompany.setString(1, customer.getName());
            createCompany.executeUpdate();
            System.out.println("The customer was added!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: Could not create new customer. Name must be unique and not empty");
        }
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void hireCar(Customer customer, Car chosenCar) {
        String hireSQL = "UPDATE \"CUSTOMER\" " +
                " SET rented_car_id = ?" +
                " WHERE id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(hireSQL)) {
            statement.setInt(1, chosenCar.getId());
            statement.setInt(2, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnRentedCar(Customer customer) {
        String hireSQL = "UPDATE \"CUSTOMER\" " +
                " SET rented_car_id = NULL" +
                " WHERE id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(hireSQL)) {
            statement.setInt(1, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
