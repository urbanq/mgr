package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class OutcomeModeLimit implements Comparable<OutcomeModeLimit>, Nameable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(OutcomeModeLimit o) {
        return name.compareToIgnoreCase(o.name);
    }
}
