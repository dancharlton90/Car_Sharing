package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        // Create database connection...
        String fileName = "defaultName";
        if (Arrays.asList(args).contains("-databaseFileName")) {
            fileName = args[Arrays.asList(args).indexOf("-databaseFileName") + 1];
        }

        // Create initial connection and get DataSource
        JdbcDataSource dataSource = Connect.createConnection(fileName);
        CompanyDao companyAccess = new CompanyDaoImpl(dataSource);

        Menu.startMenuSequence(companyAccess);
    }
}

