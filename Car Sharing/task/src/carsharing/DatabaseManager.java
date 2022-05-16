package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static JdbcDataSource dataSource;
    private static Connection sharedConnection;

    public void createConnection(String fileName) {
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "org.h2.Driver";

/*        //  Database credentials
        String USER;
        String PASS;*/

        final String DB_URL = "jdbc:h2:./src/carsharing/db/";

        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL(DB_URL.concat(fileName));
            DatabaseManager.dataSource = dataSource;

            //Open a connection
            sharedConnection = dataSource.getConnection();
            sharedConnection.setAutoCommit(true);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public void createCarTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS CAR " +
                        "(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                        " NAME VARCHAR(255) UNIQUE NOT NULL, " +
                        " COMPANY_ID INT NOT NULL, " +
                        " FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID) " +
                        ");";

        try {
            PreparedStatement statement = sharedConnection.prepareStatement(sql);
            System.out.println(sharedConnection.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCustomerTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS customer " +
                        "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                        " name VARCHAR(255) UNIQUE NOT NULL, " +
                        " rented_car_id INT, " +
                        " FOREIGN KEY (rented_car_id) REFERENCES car(id) " +
                        ");";

        try {
            PreparedStatement statement = sharedConnection.prepareStatement(sql);
            System.out.println(sharedConnection.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCompanyTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS company " +
                        "(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                        " NAME VARCHAR(255) UNIQUE NOT NULL " +
                        ");";
        try {
            Statement statement = sharedConnection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance() {
        return sharedConnection;
    }
}
