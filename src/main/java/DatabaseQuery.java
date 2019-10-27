import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DatabaseQuery {

    private final static Logger log = LoggerFactory.getLogger(DatabaseQuery.class);

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

    private static final String tableName = "EVENT";

    public void createTable() throws SQLException {

//        System.out.println(createTableSQL);
        // Step 1: Establishing a Connection
        try (Connection connection = DatabaseUtils.getConnection();
             // Step 2:Create a statement using connection object
             Statement statement = connection.createStatement();) {

            DatabaseMetaData databaseMetaData = connection.getMetaData();

            ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, null);
            // Step 3: Execute the query or update query
            if(resultSet.next()) {
                log.warn("Table {} already exists", tableName);
            } else {
                log.info("Creating table with tablename {}", tableName);
                statement.execute(createTableSQL);
            }
        } catch (SQLException e) {
            // print SQL exception information
            DatabaseUtils.printSQLException(e);
        }
    }

    public void insertRecord(String eventId, long duration, String type, String host, boolean alert) throws SQLException {
//        System.out.println(INSERT_EVENT_SQL);

        // Step 1: Establishing a Connection
        try (Connection connection = DatabaseUtils.getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_SQL)) {
            preparedStatement.setString(1, eventId);
            preparedStatement.setLong(2, duration);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, host);
            preparedStatement.setBoolean(5, alert);

//            System.out.println(preparedStatement);

            PreparedStatement st = connection.prepareStatement(
                    "SELECT * from Event WHERE id = ?");
            st.setString(1, eventId);
            ResultSet rs = st.executeQuery();

            if(!rs.next()) {
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
                log.info("Creating entry in db with eventId {}", eventId);
            } else {
                log.warn("Entry in database with eventId {} already exists!!", eventId);
            }

        } catch (SQLException e) {
            // print SQL exception information
            DatabaseUtils.printSQLException(e);
        }
    }
}