package pl.edu.agh.domain;

public class PatientFilter {
    private String quickSearch;

    private String peselContains;

    public String getPeselContains()
    {
        return peselContains;
    }

    public void setPeselContains(String peselContains)
    {
        this.peselContains = peselContains;
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
