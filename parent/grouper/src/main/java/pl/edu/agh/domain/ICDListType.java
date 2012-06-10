package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public enum ICDListType {
    G("globalna"),
    H("do grupy"),
    U("do sekcji"),
    N("negatywna");

    private final String name;

    private ICDListType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static final ICDListType valueOf(char code) {
        for(ICDListType listType : values()) {
            if(listType.name().equalsIgnoreCase(String.valueOf(code))) {
                return listType;
            }
        }
        throw new IllegalArgumentException("code = " + code);
    }
}
