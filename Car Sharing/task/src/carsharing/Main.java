package carsharing;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws SQLException {

        // Create database connection...
        String fileName = "defaultName";
        if (Arrays.asList(args).contains("-databaseFileName")) {
            fileName = args[Arrays.asList(args).indexOf("-databaseFileName") + 1];
        }

        // Create initial connection
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.createConnection(fileName);

        databaseManager.createCompanyTable();
        databaseManager.createCarTable();

        Menu.startMenuSequence();
    }
}

