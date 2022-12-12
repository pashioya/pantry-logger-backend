package int3.team2.website.pantry_loogr.domain;

public class PantryZoneProduct extends Product{

    private int quantity;
    private int amountUsed;
    private String pantryZoneName;


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

    public PantryZoneProduct(int id, String name, String productName, String code, int size, int quantity, int amountUsed, String pantryZoneName) {
        super(id, name, productName, code, size);
        this.quantity = quantity;
        this.amountUsed = amountUsed;
        this.pantryZoneName = pantryZoneName;
    }




    public int getQuantity() {
        return quantity;
    }

    public int getAmountUsed() {
        return amountUsed;
    }

    public String getPantryZoneName() { return pantryZoneName;}

    public Integer getTotalRemaining() {
        return getQuantity() * getSize() - getAmountUsed();
    }
}
