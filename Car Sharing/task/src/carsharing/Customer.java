package carsharing;

import java.util.Optional;

public class Customer {

    private int id;
    private String name;
    private Optional<Car> rentedCar;



    public Customer(String name) {
        this.name = name;
        rentedCar = null;
    }

    public Customer(int id, String name, Optional<Car> rentedCar) {
        this.id = id;
        this.name = name;
        this.rentedCar = rentedCar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<Car> getRentedCar() {
        return rentedCar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRentedCar(Car car) {
        this.rentedCar = Optional.ofNullable(car);
    }
}
