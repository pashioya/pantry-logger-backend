package int3.team2.website.pantry_loogr.domain;

import java.util.List;

/**
 * PantryZone represent a place where the User stores his food
 * It is also the place where we make our measurements
 */
public class PantryZone {
    private int id;
    private String name;
    private List<PantryZoneProduct> products;
    private int minTemp;
    private int maxTemp;
    private int minHum;
    private int maxHum;
    private int minBright;
    private int maxBright;

     public PantryZone(int id, String name) {
         this.id = id;
         this.name = name;
     }

    /**
     * Constructor to create a new PantryZone before it is inserted into the database.
     *
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
    public PantryZone(String name, int minTemp, int maxTemp, int minHum, int maxHum, int minBright, int maxBright) {
        this.name = name;
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
}
