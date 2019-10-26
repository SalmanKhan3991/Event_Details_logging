import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    private static EventLog dataLogger = new EventLog();

    public static void main(String[] args) {

        File file = new File("C:\\Users\\salma\\Documents\\logfile.txt");
        HashMap<String, ArrayList<Event>> jsonMap = getJsonMap(file, "UTF-8");
        processEventMap(jsonMap);
    }

    public static synchronized HashMap<String, ArrayList<Event>> getJsonMap(File file, String encoding) {
        Scanner scn = null;
        HashMap<String, ArrayList<Event>> eventMap = null;

        try {
            scn = new Scanner(file, encoding);
            eventMap = new HashMap<>();
            while (scn.hasNext()) {
                String x = scn.nextLine();
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(x);

                Event event = setEvent(jsonObject);

                if (null != eventMap.get(event.getId())) {
                    ArrayList<Event> existingEvents = eventMap.get(event.getId());
                    existingEvents.add(event);
                    eventMap.put(event.getId(), existingEvents);
                } else {
                    ArrayList<Event> newEvent = new ArrayList<>();
                    newEvent.add(event);
                    eventMap.put(event.getId(), newEvent);
                }
            }
            log.debug(eventMap.toString());
        } catch (FileNotFoundException ex) {
            log.error("File not found");
        } catch (JsonSyntaxException ex) {
            log.error("Error while parsing JSON");
        } finally {
            scn.close();
        }

        return eventMap;
    }

    private static Event setEvent(JsonObject jsonObject) {

        String id = jsonObject.get("id").getAsString();
        String state = jsonObject.get("state").getAsString();
        long timestamp = jsonObject.get("timestamp").getAsLong();
        JsonElement type = jsonObject.get("type");
        JsonElement host = jsonObject.get("host");

        Event event = new Event(id, state, timestamp);

        if (null != type) {
            event.setType(type.getAsString());
        }

        if (null != host) {
            event.setHost(host.getAsString());
        }

        log.debug(event.toString());

        return event;
    }

    public static void processEventMap(HashMap<String, ArrayList<Event>> eventMap) {
        Iterator itr = eventMap.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, ArrayList<Event>> entry = (Map.Entry<String, ArrayList<Event>>) itr.next();
            ArrayList<Event> events = entry.getValue();
            long duration = 0;
            boolean alert = false;
            long startTime = 0;
            long endTime = 0;
            String host = null;
            String type = null;
            for (Event event : events) {
                if (null != event.getHost()) {
                    host = event.getHost();
                }

                if (null != event.getType()) {
                    type = event.getType();
                }
                if ("STARTED".equals(event.getState())) {
                    startTime = event.getTimestamp();
                } else if ("FINISHED".equals(event.getState())) {
                    endTime = event.getTimestamp();
                }
            }

            if (endTime > 0 && startTime > 0) {
                duration = endTime - startTime;
            }

            if (duration > 4) {
                log.info("Event {} exceeded a duration of 4 seconds", entry.getKey());
                alert = true;
            }

            dataLogger.log(entry.getKey(), duration, type, host, alert);
        }
    }
}
