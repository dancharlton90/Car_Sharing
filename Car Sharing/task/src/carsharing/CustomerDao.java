package carsharing;

public interface CustomerDao<T> extends BaseDao<T> {

    void hireCar(T customer, Car chosenCar);

    void returnRentedCar(T customer);
}
