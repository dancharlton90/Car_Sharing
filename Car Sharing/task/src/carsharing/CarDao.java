package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements Dao<Car> {

    Connection conn;

    public CarDao() {
        conn = DatabaseManager.getInstance();
    }

    @Override
    public Optional<Car> get(Car car) {
        return Optional.empty();
    }

    @Override
    public List<Car> getAll() {
        List<Car> listOfCars = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            try (ResultSet cars = statement.executeQuery("SELECT * FROM CAR")) {
                while (cars.next()) {
                    String name = cars.getString("NAME");
                    int id = cars.getInt("ID");
                    int companyId = cars.getInt("COMPANY_ID");
                    listOfCars.add(new Car(id, name, companyId));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfCars;
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
                int companyId = resultSet.getInt("COMPANY_ID");
                listOfCars.add(new Car(id, name, companyId));
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
            createCar.setInt(2, car.getCompanyId());
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
