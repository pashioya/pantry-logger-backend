package int3.team2.website.pantry_loogr.domain;

import java.util.List;

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

    public PantryZone(String name, int minTemp, int maxTemp, int minHum, int maxHum, int minBright, int maxBright) {
        this.id = id;
        this.name = name;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minHum = minHum;
        this.maxHum = maxHum;
        this.minBright = minBright;
        this.maxBright = maxBright;
    }

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
