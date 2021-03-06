package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDaoJdbc implements CompanyDao<Company> {

    List<Company> listOfCompanies;
    Connection conn;

    public CompanyDaoJdbc() {
        this.conn = DatabaseManager.getInstance();
    }

    @Override
    public Optional<Company> get(Company company) {
        return Optional.empty();
    }

    @Override
    public Optional<Company> getById(int anId) {
        Optional<Company> company = Optional.empty();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM company " +
                    " WHERE id = " + anId))
            {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    company = Optional.of(new Company(name, id));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public List<Company> getAll() {
        listOfCompanies = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet companies = statement.executeQuery("SELECT * FROM COMPANY")) {
                while (companies.next()) {
                    String name = companies.getString("NAME");
                    int Id = companies.getInt("ID");
                    listOfCompanies.add(new Company(name, Id));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfCompanies;
    }

    public List<Car> getCompanyCars(Company aCompany) {
        List<Car> listOfCars = new ArrayList<>();
        String sql =
                "SELECT * FROM CAR " +
                        " WHERE company_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, aCompany.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Optional<Company> company = new CompanyDaoJdbc().getById(resultSet.getInt("COMPANY_ID"));
                listOfCars.add(new Car(id, name, company.orElse(null)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfCars;
    }

    @Override
    public List<Car> getAllAvailableCars(Company aCompany) {
        List<Car> listOfCars = new ArrayList<>();
        String sql =
                "SELECT car.id, car.name, car.company_id " +
                        " FROM car LEFT JOIN customer " +
                        " ON car.id = customer.rented_car_id " +
                        " WHERE customer.name IS NULL AND car.company_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, aCompany.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Optional<Company> company = new CompanyDaoJdbc().getById(resultSet.getInt("COMPANY_ID"));
                listOfCars.add(new Car(id, name, company.orElse(null)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfCars;
    }

    @Override
    public void save(Company company) {
        String createCompanySQL = "INSERT INTO \"COMPANY\" " +
                "(name) VALUES (?)";
        try (PreparedStatement createCompany = conn.prepareStatement(createCompanySQL)) {
            createCompany.setString(1, company.getName());
            createCompany.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Company company) {

    }

    @Override
    public void delete(Company company) {

    }
}
