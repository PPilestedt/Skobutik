public class Kund {

    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private int password;

    public Kund() { }

    public Kund (int id, String firstName, String lastName, String city, int password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}
