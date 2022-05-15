package carsharing;

public class Company {

    private String name;
    private int Id;

    public Company(String name) {
        this.name = name;
    }

    public Company(String name, int Id) {
        this.name = name;
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

}
