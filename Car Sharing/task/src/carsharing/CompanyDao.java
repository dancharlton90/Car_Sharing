package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDao implements Dao<Company>{

    List<Company> listOfCompanies;
    Connection conn;

    public CompanyDao() {
        this.conn = DatabaseManager.getInstance();
    }

    @Override
    public Optional<Company> get(Company company) {
        return Optional.empty();
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
