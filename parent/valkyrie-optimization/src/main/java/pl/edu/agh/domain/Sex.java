package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 18.05.12
 */
public enum Sex {
    MALE,
    FEMALE,
    NO_DATA;

    public static final Sex valueOf(char code) {
        for(Sex sex : values()) {
            if(String.valueOf(sex.name().charAt(0)).equalsIgnoreCase(String.valueOf(code))) {
                return sex;
            }
        }
        throw new IllegalArgumentException("code = " + code);
    }
}
