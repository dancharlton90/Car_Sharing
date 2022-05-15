package carsharing;

public class Car {

    private int Id;
    private String name;
    private int companyId;

    public Car(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }

    public Car(int id, String name, int companyId) {
        Id = id;
        this.name = name;
        this.companyId = companyId;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
