package pl.edu.agh.dao.jdbc;


public final class SQLBuilderUtil {
    private SQLBuilderUtil() {
        throw new IllegalStateException("utils constructor called");
    }

    /**
     * ignore case like
     */
    public static String like(String column, boolean and) {
        return (and ? " and" : " where")  + " upper(" + column + ") like upper(?)";
    }
}
