package carsharing;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class Menu {

    static Scanner scanner;
    private static CompanyDao<Company> companyDao;
    private static CarDao<Car> carDao;
    private static CustomerDao<Customer> customerDao;

    public static void startMenuSequence() throws SQLException {

        scanner = new Scanner(System.in);
        companyDao = new CompanyDaoJdbc();
        carDao = new CarDaoJdbc();
        customerDao = new CustomerDaoJdbc();
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
                    managerLoggedInMenu();
                    break;
                case "2":                                                       //2. Log in as customer
                    customerChooseMenu();
                    break;
                case "3":                                                       //3. Create a customer
                    mainMenuCreateCustomer();
                    break;
            }
        }
    }

    private static void mainMenuCreateCustomer() {
        System.out.println();
        System.out.println("Enter the customer name:");
        String name = scanner.nextLine();
        try {
            customerDao.save(new Customer(name));
            //System.out.println("The customer was added!");
        } catch (Exception e) {
            //System.out.println("A customer with this name already exists");
            e.printStackTrace();
        }
    }

    private static void customerChooseMenu() {
        boolean continueCustomerMenu = true;
        List<Customer> customerList = customerDao.getAll();
        while (continueCustomerMenu) {
            if (customerDao.getAll().isEmpty()) {
                System.out.println("The customer list is empty!");
                continueCustomerMenu = false;
                break;
            } else {
                System.out.println("Choose a customer:");
                int count = 1;
                for (Customer c : customerList) {
                    System.out.println(count + ". " + c.getName());
                    count++;
                }
                System.out.println("0. Back");
            }
            int selectionIndex = Integer.parseInt(scanner.nextLine());
            if (selectionIndex != 0) {
                Customer selectedCustomer = customerList.get(selectionIndex - 1);
                continueCustomerMenu = false;
                customerLoggedInMenu(selectedCustomer);
            } else {
                continueCustomerMenu = false;
                break;
            }
        }
    }

    private static void customerLoggedInMenu(Customer customer) {
        boolean continueMenu = true;
        Company chosenCompany = null;
        while (continueMenu) {
            printCustomerLoggedIn();
            String input = scanner.nextLine();
            customer = customerDao.getById(customer.getId()).get();
            switch (input) {
                case "0":
                    continueMenu = false;
                    break;
                case "1":
                    if (customer.getRentedCar().isPresent()) {
                        System.out.println("You've already rented a car!");
                        break;
                    } else {
                        chosenCompany = chooseACompanyMenu();
                    }
                    if (chosenCompany != null) {
                        chooseACarMenu(customer, chosenCompany);
                    }
                    break;
                case "2":
                    returnRentedCar(customer);
                    break;
                case "3":
                    getRentedCarInfo(customer);
                    break;
            }
        }
    }

    private static void returnRentedCar(Customer customer) {
        Optional<Customer> theCustomer = customerDao.getById(customer.getId());
        if (!theCustomer.get().getRentedCar().isPresent()) {
            System.out.println();
            System.out.println("You didn't rent a car!");
        } else {
            System.out.println();
            System.out.println("You've returned a rented car!");
            customerDao.returnRentedCar(customer);
        }
    }

    private static void getRentedCarInfo(Customer customer) {
        Optional<Customer> theCustomer = customerDao.getById(customer.getId());
        if (!theCustomer.get().getRentedCar().isPresent()) {
            System.out.println();
            System.out.println("You didn't rent a car!");
        } else {
            System.out.println();
            System.out.println("Your rented car:");
            System.out.println(theCustomer.get().getRentedCar().get().getName());
            System.out.println("Company:");
            System.out.println(theCustomer.get().getRentedCar().get().getCompany().getName());
        }



    }

    private static Company chooseACompanyMenu() {
        System.out.println("Choose a company:");
        printAllCompanies();
        System.out.println("0. Back");
        String input = scanner.nextLine();
        if (Integer.parseInt(input) == 0) {
            return null;
        } else {
            List<Company> companies = companyDao.getAll();
            return companies.get(Integer.parseInt(input) - 1);
        }

    }

    private static void chooseACarMenu(Customer customer, Company company) {
        List<Car> availableCars = companyDao.getAllAvailableCars(company);
        Car chosenCar;
        if (availableCars.isEmpty()) {
            System.out.println("No available cars in the " + company.getName() + " company");
        } else {
            System.out.println();
            System.out.println("Choose a car:");
            int count = 1;
            for (Car c : availableCars) {
                System.out.println(count + ". " +c.getName());
                count++;
            }
            System.out.println("0. Back");
            int carSelection = Integer.parseInt(scanner.nextLine());
            if (carSelection != 0) {
                chosenCar = availableCars.get(carSelection - 1);
                customerDao.hireCar(customer, chosenCar);
                System.out.println();
                System.out.println("You rented '" + chosenCar.getName() + "'");
                //System.out.println(chosenCar.getName());
                //System.out.println("Company:");
                //System.out.println(company.getName());
            }

        }


    }

    private static void printCustomerLoggedIn() {
        System.out.println();
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
    }

    private static void managerLoggedInMenu() throws SQLException {
        boolean continueManagerLoop = true;
        while (continueManagerLoop) {
            printMainOptions();
            String input = scanner.nextLine();
            switch (input) {
                case "0":                                                       //0. Back
                    continueManagerLoop = false;
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
                case "0":                                                       //0. Back
                    continueCompanyLoop = false;
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
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
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

    private static void printAllCompanies() {
        List<Company> companies = companyDao.getAll();
        int count = 1;
        for (Company c : companies) {
            System.out.println(count + ". " + c.getName());
            count++;
        }
    }

    private static void mainMenuListCompanies() {
        List<Company> companies;
        companies = companyDao.getAll();
        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println();
            System.out.println("Choose the company:");
            printAllCompanies();
            System.out.println("0. Back");
            int choiceIndex = Integer.parseInt(scanner.nextLine());
            if (choiceIndex != 0) {
                Company chosenCompany = companies.get(choiceIndex - 1);
                selectCompanyMenu(chosenCompany);
            }
        }
    }

    private static void companyMenuListCars(Company aCompany) {
        List<Car> cars = companyDao.getCompanyCars(aCompany);
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println();
            System.out.println("Car list:");
            int count = 0;
            for (Car c : cars) {
                if (true) {  //testing
                    System.out.println(count+1 + ". " + c.getName());
                    count++;
                }
            }
        }
    }

    private static void companyMenuNewCar(Company aCompany) {
        System.out.println("Enter the car name:");
        String carName = scanner.nextLine();
        Car aCar = new Car(carName, aCompany);
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
