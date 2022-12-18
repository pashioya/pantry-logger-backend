package int3.team2.website.pantry_loogr.domain;

import java.util.List;
import java.util.Map;

/**
 * ShoppingList is a list of ingredients (and their amount) that the user needs to buy.
 * It is tied to the user in the DB
 */
public class ShoppingList {
    private int id;
    private Map<Ingredient, Integer> ingredients;

    /**
     * Constructor used when getting a ShoppingList from the database with id included
     * @param id id of the shopping list
     * @param ingredients ingredients and their amount in that shopping list
     */
    public ShoppingList(int id, Map<Ingredient, Integer> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public ShoppingList(int id) {
        this.id = id;
    }
    public ShoppingList() {
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

    public void setId(int id) {
        this.id = id;
    }
}
