package int3.team2.website.pantry_loogr.domain;

public class ShoppingListIngredient extends Ingredient {
    private int amount;

    public ShoppingListIngredient(String name, int amount) {
        super(name);
        this.amount = amount;
    }

    public ShoppingListIngredient(int id, String name, int amount) {
        super(id, name);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
