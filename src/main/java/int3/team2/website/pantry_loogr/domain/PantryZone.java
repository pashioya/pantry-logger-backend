package int3.team2.website.pantry_loogr.domain;

import java.util.List;
import java.util.Map;

/**
 * PantryZone represent a place where the User stores his food
 * It is also the place where we make our measurements
 */
public class PantryZone {
    private int id;

    private int userId;
    private String name;
    private String sensorBoxCode = "";
    private List<PantryZoneProduct> products;
    private int minTemp;
    private int maxTemp;
    private int minHum;
    private int maxHum;
    private int minBright;
    private int maxBright;
    private int latestTemp;
    private int latestHum;
    private int latestBright;
    private boolean enviroOutOfRange = false;

     public PantryZone(int id, String name) {
         this.id = id;
         this.name = name;
     }

    /**
     * Constructor to create a new PantryZone before it is inserted into the database.
     *
     * @param name name of the zone (e.g. fridge, pantry, fruitBowl, ...),
     * @param userId id of the user that owns the zone
     * @param minTemp if the recorded temperature in that zone goes under this value,
     *                it will send a notification to the user
     * @param maxTemp if the recorded temperature in that zone goes over this value,
     *                it will send a notification to the user
     * @param minHum if the recorded humidity in that zone goes under this value,
     *               it will send a notification to the user
     * @param maxHum if the recorded humidity in that zone goes over this value,
     *               it will send a notification to the user
     * @param minBright if the recorded brightness in that zone goes under this value,
     *                  it will send a notification to the user
     * @param maxBright if the recorded brightness in that zone goes over this value,
     *                  it will send a notification to the user
     */
    public PantryZone(String name,int userId, int minTemp, int maxTemp, int minHum, int maxHum, int minBright, int maxBright) {
        this.name = name;
        this.userId = userId;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minHum = minHum;
        this.maxHum = maxHum;
        this.minBright = minBright;
        this.maxBright = maxBright;
    }

    /**
     * Constructor used when getting a pantryZone from the database with id included.
     *
     * @param id ties the pantryZone to the DB
     * @param name name of the zone (e.g. fridge, pantry, fruitBowl, ...)
     * @param minTemp if the recorded temperature in that zone goes under this value,
     *                it will send a notification to the user
     * @param maxTemp if the recorded temperature in that zone goes over this value,
     *                it will send a notification to the user
     * @param minHum if the recorded humidity in that zone goes under this value,
     *               it will send a notification to the user
     * @param maxHum if the recorded humidity in that zone goes over this value,
     *               it will send a notification to the user
     * @param minBright if the recorded brightness in that zone goes under this value,
     *                  it will send a notification to the user
     * @param maxBright if the recorded brightness in that zone goes over this value,
     *                  it will send a notification to the user
     */
    public PantryZone(int id, String name, int minTemp, int maxTemp, int minHum, int maxHum, int minBright, int maxBright) {
        this.id = id;
        this.name = name;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minHum = minHum;
        this.maxHum = maxHum;
        this.minBright = minBright;
        this.maxBright = maxBright;
    }

    public void setProducts(List<PantryZoneProduct> products) {
        this.products = products;
    }

    public List<PantryZoneProduct> getProducts() {
        return products;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinHum() {
        return minHum;
    }

    public int getMaxHum() {
        return maxHum;
    }

    public int getMinBright() {
        return minBright;
    }

    public int getMaxBright() {
        return maxBright;
    }

    public int getLatestTemp() {
        return latestTemp;
    }

    public int getLatestHum() {
        return latestHum;
    }

    public int getLatestBright() {
        return latestBright;
    }

    public int getUserId() {
        return userId;
    }
    public boolean isEnviroOutOfRange() {
        return enviroOutOfRange;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinHum(int minHum) {
        this.minHum = minHum;
    }

    public void setMaxHum(int maxHum) {
        this.maxHum = maxHum;
    }

    public void setMinBright(int minBright) {
        this.minBright = minBright;
    }

    public void setMaxBright(int maxBright) {
        this.maxBright = maxBright;
    }

    public void setEnviro(Map<String, Integer> latest) {
        this.latestTemp = latest.get("temp");
        this.latestHum = latest.get("hum");
        this.latestBright = latest.get("bright");

        if((latestTemp > maxTemp || latestTemp < minTemp)
            || (latestHum > maxHum || latestHum < minHum)
            || (latestBright > maxBright || latestBright < minBright)) {
            enviroOutOfRange = true;
        }
    }

    public void setSensorBoxCode(String sensorBoxCode) {
        this.sensorBoxCode = sensorBoxCode;
    }

    public String getSensorBoxCode() {
        return this.sensorBoxCode;
    }

    public boolean isSelectedLocation(String location) {
        return location.equals(this.name);
    }
}
