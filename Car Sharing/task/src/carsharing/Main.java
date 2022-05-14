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

        loginMenu();
    }


    private static void loginMenu () {
        boolean continueLoginLoop = true;
        while (continueLoginLoop) {
            printLoginOptions();
            String input = scanner.nextLine();
            switch (input) {
                case "1":                                                       //1. Log in as a manager
                    mainMenu();
                    break;
                case "0":                                                       //0. Exit
                    continueLoginLoop = false;
                    break;
            }
        }
    }

    private static void mainMenu() {
        boolean continueMenuLoop = true;
        while (continueMenuLoop) {
            printMainOptions();
            String input = scanner.nextLine();
            switch (input) {
                case "1":                                                       //1. Company list
                    if (companyAccess.getAllCompanies() == null || companyAccess.getAllCompanies().isEmpty()) {
                        System.out.println("The company list is empty!");
                    } else {
                        System.out.println();
                        System.out.println("Company list:");
                        int count = 1;
                        for (Company c : companyAccess.getAllCompanies()) {
                            System.out.println(count + ". " + c.getName());
                            count++;
                        }
                    }
                    break;
                case "2":                                                       //2. Create a company
                    System.out.println("Enter the company name:");
                    String companyName = scanner.nextLine();
                    companyAccess.createCompany(companyName);
                    System.out.println("The company was created!");
                    break;
                case "0":                                                       //0. Back
                    continueMenuLoop = false;
                    break;
            }
        }
        loginMenu();
    }

    private static void printLoginOptions() {
        System.out.println();
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
        System.out.println();
    }

    private static void printMainOptions() {
        System.out.println();
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
        System.out.println();
    }
}

