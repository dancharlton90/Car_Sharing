package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao{

    List<Company> listOfCompanies = new ArrayList<>();
    JdbcDataSource dataSource;

    String createCompanySQL = "INSERT INTO \"COMPANY\" " +
            "(name) VALUES (?)";

    public CompanyDaoImpl(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Company> getAllCompanies() {
        listOfCompanies = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return listOfCompanies;
    }

    @Override
    public Company getCompany() {
        return null;
    }

    @Override
    public void createCompany(String name) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement createCompany = conn.prepareStatement(createCompanySQL)) {
                createCompany.setString(1, name);
                createCompany.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateCompany(Company company) {

    }

    @Override
    public void deleteCompany(Company company) {

    }
}
