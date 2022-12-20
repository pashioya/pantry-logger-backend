package int3.team2.website.pantry_loogr.domain;

/**
 * Product is an extension of Ingredient,
 * instead of just containing the broad information about a product,
 * it also holds how much of that product is in one pack and what barCode it is associated to
 */
public class Product extends Ingredient {
    protected int productId;
    protected String productName;
    protected String code;
    protected int size;
    protected int productId;

    /**
     * Constructor to create a new Product before it is inserted into the database.
     *
     * @param name the name of the ingredient
     * @param productName the name of the product (is often different from the ingredient it extends)
     * @param code the barcode associated with the product
     * @param size teh quantity of the product in one pack
     */
    public Product(String name, String productName, String code, int size) {
        super(name);
        this.productName = productName;
        this.code = code;
        this.size = size;
    }

    /**
     * Constructor used when getting a product from the database with id included
     *
     * @param id ties the ingredient to the DB
     * @param name name of the ingredient
     * @param productName the name of the product (is often different from the ingredient it extends)
     * @param code the barcode associated with the product
     * @param size teh quantity of the product in one pack
     * @param imagePath used to tie the ingredient to the image
     */

    public Product(int ingredientId, String name, String productName, String code, int size, String imagePath) {
        super(ingredientId, name, imagePath);
        this.productName = productName;
        this.code = code;
        this.size = size;
    }

    public Product(int ingredientId, int productId, String name, String productName, String code, int size, String imagePath) {
        super(ingredientId, name, imagePath);
        this.productId = productId;
        this.productName = productName;
        this.code = code;
        this.size = size;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getProductName() {
        return productName;
    }

    public String getCode() {
        return code;
    }

    public int getSize() {
        return size;
    }

    public int getProductId() { return productId; }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", code='" + code + '\'' +
                ", size=" + size +
                ", productId=" + productId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
