package pl.edu.agh.domain;

public class Patient implements Comparable<Patient> {
    private Integer id;
    private String firstName;
    private String lastName;
    private String pesel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public int compareTo(Patient o) {
        return Long.valueOf(pesel).compareTo(Long.valueOf(o.pesel));
    }
}
