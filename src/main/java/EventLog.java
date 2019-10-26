import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class EventLog {

    private final static Logger log = LoggerFactory.getLogger(EventLog.class);
    private static DatabaseQuery dbObject = new DatabaseQuery();

    public EventLog() {
        initDatabase();
    }

    public void log(String id, long duration, String type, String host, boolean alert) {
        try {
            dbObject.insertRecord(id, duration, type, host, alert);
        } catch (SQLException e) {
            log.error("Error while logging data to database");
        }
    }

    public void initDatabase() {
        try {
            dbObject.createTable();
        } catch (SQLException e) {
            log.error("Error while creating table");
        }

    }
}
