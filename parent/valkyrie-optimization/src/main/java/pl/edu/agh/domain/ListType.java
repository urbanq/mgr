package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public enum ListType {
    /**
     * globalna
     */
    G,
    /**
     * do grupy
     */
    H,
    /**
     * do sekcji
     */
    U,
    /**
     * negatywna
     */
    N;

    public static final ListType valueOf(char code) {
        for(ListType listType : values()) {
            if(listType.name().equalsIgnoreCase(String.valueOf(code))) {
                return listType;
            }
        }
        throw new IllegalArgumentException("code = " + code);
    }
}
