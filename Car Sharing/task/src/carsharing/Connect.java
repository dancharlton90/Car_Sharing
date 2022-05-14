package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

    //TODO Split table creation into it's own method. Maybe rename class to connection manager?

    private static JdbcDataSource sharedDataSource;

    public static JdbcDataSource createConnection(String fileName) {
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "org.h2.Driver";

        //  Database credentials
        String USER = "";
        String PASS = "";

        final String DB_URL = "jdbc:h2:./src/carsharing/db/";

        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL(DB_URL.concat(fileName));
            sharedDataSource = dataSource;

            //Open a connection
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            //Execute a query
            stmt = conn.createStatement();
            String sql =
                    "CREATE TABLE IF NOT EXISTS COMPANY " +
                            "(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                            " NAME VARCHAR(255) UNIQUE NOT NULL)";
            stmt.executeUpdate(sql);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // finally used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        return sharedDataSource;
    }
}
