package view.tm;

public class CustomerTM implements Comparable<CustomerTM>{
    private String id;
    private String name;
    private String address;
    private String salary;

    public CustomerTM() {

    }

    public CustomerTM(String id, String name, String address, String salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }

    @Override
    public int compareTo(CustomerTM o) {
        return id.compareTo(o.getId());
    }
}
