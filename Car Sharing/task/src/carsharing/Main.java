package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class Main {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";


    //  Database credentials
    static final String USER = "";
    static final String PASS = "";

    public static void main(String[] args) {

        String DB_URL = "jdbc:h2:./src/carsharing/db/";

        if (Arrays.asList(args).contains("-databaseFileName")) {
            String filename = args[Arrays.asList(args).indexOf("-databaseFileName") + 1];
            DB_URL = DB_URL.concat(filename);
        } else {
            DB_URL = DB_URL.concat("defaultName");
        }

        Connection conn = null;
        Statement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);

            //Execute a query
            stmt = conn.createStatement();
            String sql =
                    "CREATE TABLE COMPANY " +
                            "(ID INT, " +
                            " NAME VARCHAR(255))";
            stmt.executeUpdate(sql);

            //Clean up environment
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        }

    }
}