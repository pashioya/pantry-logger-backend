package int3.team2.website.pantry_loogr.domain;

import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * PantryZoneProduct is an extension of Product
 * It has the same information but on top of that also contains information
 * on how much of it the User has and where the User stores the product
 */
public class PantryZoneProduct extends Product{
    private int quantity;
    private int amountUsed;
    private PantryZone pantryZone;
    private LocalDate dateEntered;

    /**
     * Constructor to create a new PantryZoneProduct before it is inserted into the database.
     * @param name the name of the ingredient
     * @param productName the name of the product (is often different from the ingredient it extends)
     * @param code the barcode associated with the product
     * @param size teh quantity of the product in one pack
     * @param quantity the quantity that the User has
     * @param amountUsed the amount that has been used up by the owner
     */
    public PantryZoneProduct(String name, String productName, String code, int size, int quantity, int amountUsed) {
        super(name, productName, code, size);
        this.quantity = quantity;
        this.amountUsed = amountUsed;
    }

    /**
     * Constructor used when getting a PantryZoneProduct from the database with id included
     * @param id ties the ingredient to the DB
     * @param name name of the ingredient
     * @param productName the name of the product (is often different from the ingredient it extends)
     * @param code the barcode associated with the product
     * @param size teh quantity of the product in one pack
     * @param quantity the quantity that the User has
     * @param amountUsed the amount that has been used up by the owner
     * @param imagePath used to tie the ingredient to the image
     */
    public PantryZoneProduct(int id, int productId, String name, String productName, String code, int size, int quantity, int amountUsed, String imagePath) {
        super(id, productId, name, productName, code, size, imagePath);
        this.quantity = quantity;
        this.amountUsed = amountUsed;
    }

    /**
     * Constructor used when getting a PantryZoneProduct from the database with id included
     * (when there is a pantryZone and a dateEntered tied to the obj)
     * @param id ties the ingredient to the DB
     * @param name name of the ingredient
     * @param productName the name of the product (is often different from the ingredient it extends)
     * @param code the barcode associated with the product
     * @param size teh quantity of the product in one pack
     * @param quantity the quantity that the User has
     * @param amountUsed the amount that has been used up by the owner
     * @param pantryZone the pantry zone in which the product is stored
     * @param dateEntered the date at which the product has been entered into the DB
     *                    (useful when calculating if a product is about to go bad)
     * @param imagePath used to tie the ingredient to the image
     */
    public PantryZoneProduct(int id, int productId, String name, String productName, String code, int size, int quantity, int amountUsed, PantryZone pantryZone, LocalDate dateEntered, String imagePath) {
        super(id, productId, name, productName, code, size, imagePath);
        this.quantity = quantity;
        this.amountUsed = amountUsed;
        this.pantryZone = pantryZone;
        this.dateEntered = dateEntered;
    }

    public PantryZoneProduct(Product product, int quantity, int amountUsed, LocalDate dateEntered) {
        super(product);
        this.quantity = quantity;
        this.amountUsed = amountUsed;
        this.dateEntered = dateEntered;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmountUsed() {
        return amountUsed;
    }

    public Integer getTotalRemaining() {
        return getQuantity() * getSize() - getAmountUsed();
    }

    public PantryZone getPantryZone() { return pantryZone;}

    public LocalDate getDateEntered() { return dateEntered; }

    public void setAmountUsed(int amountUsed) {
        this.amountUsed = amountUsed;
    }

    public boolean setQuantity(int quantity) {
        if (quantity <= 0) {
            LoggerFactory.getLogger(this.getClass()).debug("quantity less then 0");
            return false;
        }
        this.quantity = quantity;
        return true;
    }

    /**
    * Returns true or false based on the final value of quantity.
    * If the value of quantity is below 0 it will return false
    * if the value is above 0 it will return true.
    * */
    public boolean removeFromQuantity(int amountToRemove) {
        this.quantity = this.quantity - amountToRemove;
        return quantity > 0;
    }

    public int getProductId() {
        return productId;
    }

    public Product getProduct() {
        return new Product(
                super.getIngredient(),
                this.productId,
                this.productName,
                this.size,
                this.code
        );
    }

    public boolean combine(PantryZoneProduct product) {
        if (this.getProductId() != product.getProductId()) {
            return false;
        }

        this.quantity += product.getQuantity();
        if ((this.getAmountUsed() + product.getAmountUsed()) >= this.getSize()) {
            this.quantity -= 1;
            this.amountUsed = this.getAmountUsed() + product.getAmountUsed() - this.getSize();
        } else {
            this.amountUsed = this.getAmountUsed() + product.getAmountUsed();
        }

        this.dateEntered = LocalDate.now();
        return true;
    }

    public boolean equals(PantryZoneProduct product) {
        return
                this.getPantryZone().getId() == product.getPantryZone().getId()
                && this.getProductId() == product.getProductId()
                && this.getAmountUsed() == product.getAmountUsed();
    }


    public void setPantryZone(PantryZone pantryZone) {
        this.pantryZone = pantryZone;
    }
}
