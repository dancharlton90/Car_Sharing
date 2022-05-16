package carsharing;

public class Car {

    private int Id;
    private String name;
    private Company company;

    public Car(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public Car(int id, String name, Company company) {
        Id = id;
        this.name = name;
        this.company = company;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
