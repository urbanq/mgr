package pl.edu.agh.dao.jdbc;


public final class SQLBuilderUtil {
    private SQLBuilderUtil() {
        throw new IllegalStateException("utils constructor called");
    }

    /**
     * ignore case liek
     */
    public static String andLike(String column, boolean and) {
        return (and ? " and" : " where")  + " upper(" + column + ") like upper(?)";
    }

    /**
     * ignore case liek
     */
    public static String orLike(String column, boolean or) {
        return (or ? " or" : " where")  + " upper(" + column + ") like upper(?)";
    }
}
