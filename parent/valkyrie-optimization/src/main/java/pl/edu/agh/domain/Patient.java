package pl.edu.agh.domain;

public class Patient implements Comparable<Patient> {
    private Integer id;
    private String firstname;
    private String lastname;
    private String pesel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
