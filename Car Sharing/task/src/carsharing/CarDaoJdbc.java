package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoJdbc implements CarDao<Car> {

    Connection conn;

    public CarDaoJdbc() {
        conn = DatabaseManager.getInstance();
    }

    @Override
    public Optional<Car> get(Car car) {
        return Optional.empty();
    }

    @Override
    public Optional<Car> getById(int anId) {
        Optional<Car> car = Optional.empty();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM car " +
                    " WHERE id = " + anId))
            {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Optional<Company> company = new CompanyDaoJdbc().getById(resultSet.getInt("company_id"));
                    Company company2 = company.get();
                    car = Optional.of(new Car(id, name, company2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    @Override
    public List<Car> getAll() {
        List<Car> listOfCars = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet result = statement.executeQuery("SELECT * FROM CAR")) {
                while (result.next()) {
                    String name = result.getString("NAME");
                    int id = result.getInt("ID");
                    Optional<Company> company = new CompanyDaoJdbc().getById(result.getInt("COMPANY_ID"));
                    listOfCars.add(new Car(id, name, company.orElse(null)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfCars;
    }



    @Override
    public void save(Car car) {
        String createSQL = "INSERT INTO \"CAR\" " +
                "(NAME, COMPANY_ID) VALUES (?, ?)";
        try (PreparedStatement createCar = conn.prepareStatement(createSQL)) {
            createCar.setString(1, car.getName());
            createCar.setInt(2, car.getCompany().getId());
            createCar.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Car car) {}

    @Override
    public void delete(Car car) {}
}
