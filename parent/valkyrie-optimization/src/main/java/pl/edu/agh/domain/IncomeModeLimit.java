package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class IncomeModeLimit implements Comparable<IncomeModeLimit> {
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
    public int compareTo(IncomeModeLimit o) {
        return name.compareToIgnoreCase(o.name);
    }
}
