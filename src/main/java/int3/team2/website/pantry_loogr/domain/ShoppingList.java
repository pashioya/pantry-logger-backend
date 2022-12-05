package int3.team2.website.pantry_loogr.domain;

import java.util.List;

public class ShoppingList {
    private int id;
    private List<ShoppingListIngredient> ingredients;

    public ShoppingList(int id, List<ShoppingListIngredient> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public ShoppingList(int id) {
        this.id = id;
    }

    public void addIngredient(ShoppingListIngredient ingredient) {
        ingredients.add(ingredient);
    }

    public void setIngredients(List<ShoppingListIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public List<ShoppingListIngredient> getIngredients() {
        return ingredients;
    }
}
