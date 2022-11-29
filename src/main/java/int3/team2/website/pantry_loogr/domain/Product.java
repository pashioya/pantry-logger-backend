package int3.team2.website.pantry_loogr.domain;

public class Product extends Ingredient {
    protected String productName;
    protected String code;
    protected int size;

    public Product(String name, String productName, String code, int size) {
        super(name);
        this.productName = productName;
        this.code = code;
        this.size = size;
    }

    public Product(int id, String name, String productName, String code, int size) {
        super(id, name);
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
}
