package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static CompanyDao companyAccess;
    static JdbcDataSource dataSource;
    static Scanner scanner;

    public static void main(String[] args) {

        // Create database connection
        String fileName = "defaultName";
        if (Arrays.asList(args).contains("-databaseFileName")) {
            fileName = args[Arrays.asList(args).indexOf("-databaseFileName") + 1];
        }
        dataSource = Connect.createConnection(fileName);
        companyAccess = new CompanyDaoImpl(dataSource);
        scanner = new Scanner(System.in);

        Menu.startMenuSequence(companyAccess);
    }
}

