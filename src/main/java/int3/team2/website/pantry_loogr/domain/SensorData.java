package int3.team2.website.pantry_loogr.domain;

import java.time.LocalDateTime;


/**
 * Represents one measurement of one sensor of one sensor-box.
 */
public class SensorData {
    private int id;
    private int pantryZoneId;
    private LocalDateTime timestamp;
    private SensorType type;
    private int value;

    /**
     * Constructor for creating a new sensor data object that does not exist in the DB yet
     * @param timestamp - timestamp of the recieved data
     * @param type - SensorType Enum of the type of sensor (HUMIDITY, TEMPERATURE, BRIGHTNESS)
     * @param value - the raw value from the sensor
     */
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

    /**
     * Constructor for retrieving from the DB
     * @param timestamp - timestamp of the recieved data
     * @param type - SensorType Enum of the type of sensor (HUMIDITY, TEMPERATURE, BRIGHTNESS)
     * @param value - the raw value from the sensor
     * @param pantryZoneId - the id of the pantryzone that this sensor value is for
     */
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
