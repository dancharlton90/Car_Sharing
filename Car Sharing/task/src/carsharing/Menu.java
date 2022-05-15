package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public final class Menu {

    static Scanner scanner;
    private static CompanyDao companyDao;
    private static CarDao carDao;

    public static void startMenuSequence() throws SQLException {

        scanner = new Scanner(System.in);
        companyDao = new CompanyDaoJdbc();
        carDao = new CarDaoJdbc();
        loginMenu();
    }

    private static void loginMenu () throws SQLException {
        boolean continueLoginLoop = true;
        while (continueLoginLoop) {
            printLoginOptions();
            String input = scanner.nextLine();
            switch (input) {
                case "0":                                                       //0. Exit
                    continueLoginLoop = false;
                    break;
                case "1":                                                       //1. Log in as a manager
                    mainMenu();
                    break;
            }
        }
    }

    private static void mainMenu() throws SQLException {
        boolean continueMenuLoop = true;
        while (continueMenuLoop) {
            printMainOptions();
            String input = scanner.nextLine();
            switch (input) {
                case "0":                                                       //0. Back
                    continueMenuLoop = false;
                    break;
                case "1":                                                       //1. Company list
                    mainMenuListCompanies();
                    break;
                case "2":                                                       //2. Create a company
                    mainMenuNewCompany();
                    break;
            }
        }
        loginMenu();
    }

    private static void selectCompanyMenu(Company aCompany) {
        boolean continueCompanyLoop = true;
        while (continueCompanyLoop) {
            printCompanyMenuOptions(aCompany);
            String input = scanner.nextLine();
            switch (input) {
                case "0":
                    continueCompanyLoop = false;                               //0. Back
                    break;
                case "1":                                                       //1. Car list
                    companyMenuListCars(aCompany);
                    break;
                case "2":                                                       //2. Create car
                    companyMenuNewCar(aCompany);
                    break;
            }
        }
    }

    private static void printCompanyMenuOptions(Company aCompany) {
        System.out.println();
        System.out.println("'" + aCompany.getName() + "' company");
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }

    private static void printLoginOptions() {
        System.out.println();
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
        //System.out.println();
    }

    private static void printMainOptions() {
        System.out.println();
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
        //System.out.println();
    }

    private static void mainMenuListCompanies() {
        List<Company> companies;
        if (companyDao.getAll() == null || companyDao.getAll().isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println();
            System.out.println("Choose the company:");
            companies = companyDao.getAll();
            int count = 1;
            for (Company c : companies) {
                System.out.println(count + ". " + c.getName());
                count++;
            }
            System.out.println("0. Back");
            int choiceIndex = Integer.parseInt(scanner.nextLine());
            if (choiceIndex != 0) {
                Company chosenCompany = companies.get(choiceIndex - 1);
                selectCompanyMenu(chosenCompany);
            }
        }
    }

    private static void companyMenuListCars(Company aCompany) {
        List<Car> cars;
        if (companyDao.getCompanyCars(aCompany).isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println();
            System.out.println("Car list:");
            cars = carDao.getAll();
            int count = 0;
            for (Car c : cars) {
                if (c.getCompanyId() == aCompany.getId()) {
                    System.out.println(count+1 + ". " + c.getName());
                    count++;
                }
            }
        }
    }

    private static void companyMenuNewCar(Company aCompany) {
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine();
        Car aCar = new Car(carName, aCompany.getId());
        carDao.save(aCar);
        System.out.println("The car was added!");
    }

    private static void mainMenuNewCompany() {
        System.out.println("Enter the company name:");
        String companyName = scanner.nextLine();
        Company aCompany = new Company(companyName);
        companyDao.save(aCompany);
        System.out.println("The company was created!");
    }
}
