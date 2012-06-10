package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public enum Service {
    /**
     * Leczenie stacjonarne - Pobyt na oddziale szpitalnym
     */
    BASE_HOSPITAL(0.1f),
    /**
     * Leczenie stacjonarne - Osoba leczona
     */
    BASE_Person(0.11f),
    /**
     * Leczenie ambulatoryjne specjalne - Porada
     */
    AMBULATORY(4.4f),
    /**
     * Pozostała opieka ambulatoryjna - Porada
     */
    OTHER_AMBULATORY(7.4f),
    /**
     * Świadczenia w domu pacjenta - Porada
     */
    HOME(8.4f),
    /**
     * Inne
     */
    NULL(0.0f)
    ;
    private Service(float code) {
        this.code = code;
    }
    private final float code;
    private String message;

    public float getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final Service valueOf(Float code) {
        if(code == null) {
            return NULL;
        }
        for(Service service : values()) {
            if(service.code == code) {
                return service;
            }
        }
        throw new IllegalArgumentException("code = " + code);
    }

    @Override
    public String toString() {
        return message;
    }
}
