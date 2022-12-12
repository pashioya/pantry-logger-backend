package int3.team2.website.pantry_loogr.domain;

import java.util.List;
import java.util.Map;

public class ShoppingList {
    private int id;
    private Map<Ingredient, Integer> ingredients;

    public ShoppingList(int id, Map<Ingredient, Integer> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public ShoppingList(int id) {
        this.id = id;
    }

    public void addIngredient(Ingredient ingredient, int amount) {
        ingredients.put(ingredient, amount);
    }


    public void setIngredients( Map<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public  Map<Ingredient, Integer> getIngredients() {
        return ingredients;
    }
}
