package int3.team2.website.pantry_loogr.domain;

public class PantryZoneProduct extends Product{

    private int quantity;
    private int amountUsed;
    private PantryZone pantryZone;


    public PantryZoneProduct(String name, String productName, String code, int size, int quantity, int amountUsed) {
        super(name, productName, code, size);
        this.quantity = quantity;
        this.amountUsed = amountUsed;
    }

    public PantryZoneProduct(int id, String name, String productName, String code, int size, int quantity, int amountUsed) {
        super(id, name, productName, code, size);
        this.quantity = quantity;
        this.amountUsed = amountUsed;
    }

    public PantryZoneProduct(int id, String name, String productName, String code, int size, int quantity, int amountUsed, PantryZone pantryZone) {
        super(id, name, productName, code, size);
        this.quantity = quantity;
        this.amountUsed = amountUsed;
        this.pantryZone = pantryZone;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmountUsed() {
        return amountUsed;
    }

    public PantryZone getPantryZone() { return pantryZone;}

    public void setAmountUsed(int amountUsed) {
        this.amountUsed = amountUsed;
    }
}
