package int3.team2.website.pantry_loogr.domain;

public class Item {
    private int id;
    private String name;
    private String code;
    private int size;
    private int quantity;
    private Ingredient ingredient;
    private int ingredientId;

    /**
     *
     * @param name name of the item
     * @param code
     * @param size
     * @param quantity
     * @param ingredientId
     */
    public Item(String name, String code, int size, int quantity, int ingredientId) {
        this.name = name;
        this.code = code;
        this.size = size;
        this.quantity = quantity;
        this.ingredientId = ingredientId;
    }
    public Item(int id, String name, String code, int size, int ingredientId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.size = size;
        this.ingredientId = ingredientId;
    }
    public Item(int id, String name, String code, int size, int quantity, int ingredientId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.size = size;
        this.quantity = quantity;
        this.ingredientId = ingredientId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getIngredientId() {
        return ingredientId;
    }


    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getSize() {
        return size;
    }
    public int getQuantity() {
        return quantity;
    }
}
