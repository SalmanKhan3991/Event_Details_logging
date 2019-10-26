import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {

    private static final String createTableSQL = "CREATE TABLE Event (\n" +
            "Id VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
            "Duration INTEGER NOT NULL,\n" +
            "Type VARCHAR(50),\n" +
            "Host VARCHAR(20),\n" +
            "Alert BOOLEAN NOT NULL\n" +
            ");";

    private static final String INSERT_EVENT_SQL = "INSERT INTO Event" +
            "  (Id, Duration, Type, Host, Alert) VALUES " +
            " (?, ?, ?, ?, ?);";

    public void createTable() throws SQLException {

        System.out.println(createTableSQL);
        // Step 1: Establishing a Connection
        try (Connection connection = DatabaseUtils.getConnection();
             // Step 2:Create a statement using connection object
             Statement statement = connection.createStatement();) {

            // Step 3: Execute the query or update query
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            // print SQL exception information
            DatabaseUtils.printSQLException(e);
        }
    }

    public void insertRecord(String eventId, long duration, String type, String host, boolean alert) throws SQLException {
        System.out.println(INSERT_EVENT_SQL);

        // Step 1: Establishing a Connection
        try (Connection connection = DatabaseUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_SQL)) {
            preparedStatement.setString(1, eventId);
            preparedStatement.setLong(2, duration);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, host);
            preparedStatement.setBoolean(5, alert);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            DatabaseUtils.printSQLException(e);
        }
    }
}
