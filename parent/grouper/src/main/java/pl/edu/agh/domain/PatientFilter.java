package pl.edu.agh.domain;

import pl.edu.agh.dao.GenericFilter;

public class PatientFilter implements GenericFilter<Patient> {
    private String quickSearch;
    private String pesel;
    private String firstname;
    private String lastname;

    public String getPesel()
    {
        return pesel;
    }

    public void setPesel(String pesel)
    {
        this.pesel = pesel;
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

    public String getQuickSearch()
    {
        return quickSearch;
    }

    public void setQuickSearch(String quickSearch)
    {
        this.quickSearch = quickSearch;
    }
}
