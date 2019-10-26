public class Event {

    private String id;
    private String state;
    private String type;
    private String host;
    private long timestamp;

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Event(String id, String state, long timestamp) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
