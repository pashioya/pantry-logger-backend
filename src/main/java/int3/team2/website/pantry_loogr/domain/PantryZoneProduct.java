package int3.team2.website.pantry_loogr.domain;

public class PantryZoneProduct extends Product{

    private int quantity;
    private int amountUsed;

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

    public int getQuantity() {
        return quantity;
    }

    public int getAmountUsed() {
        return amountUsed;
    }
}
