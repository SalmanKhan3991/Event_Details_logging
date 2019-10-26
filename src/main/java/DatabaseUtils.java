import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    private static final String databaseURL = "jdbc:hsqldb:hsql://localhost/testdb";

    private static final String databaseUsername = "SA";

    private static final String databasePassword = "";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            System.out.println("URL: " + databaseURL + " Username: " + databaseUsername + " Password: " + databasePassword);
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        } catch (SQLException e) {
            log.error("SQL Exception");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.error("Class not found exception");
            e.printStackTrace();
        }
        return connection;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
