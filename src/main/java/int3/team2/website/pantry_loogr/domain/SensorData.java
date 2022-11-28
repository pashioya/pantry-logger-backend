package int3.team2.website.pantry_loogr.domain;

import java.time.LocalDateTime;

public class SensorData {
    private int id;
    private int pantryZoneId;
    private LocalDateTime timestamp;
    private SensorType type;
    private int value;


    public SensorData(LocalDateTime timestamp, SensorType type, int value) {
        this.timestamp = timestamp;
        this.type = type;
        this.value = value;
    }

    public SensorData(int id, LocalDateTime timestamp, SensorType type, int value) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.value = value;
    }

    public SensorData(int id, LocalDateTime timestamp, SensorType type, int value, int pantryZoneId) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.value = value;
        this.pantryZoneId = pantryZoneId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public SensorType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
