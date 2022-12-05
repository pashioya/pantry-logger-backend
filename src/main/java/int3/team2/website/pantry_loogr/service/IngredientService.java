package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.Product;
import int3.team2.website.pantry_loogr.domain.ShoppingListIngredient;

import java.util.List;
import java.util.Map;

public interface IngredientService {
    List<Ingredient> getAll();
    Ingredient get(int ingredientID);
    List<Ingredient> add(Ingredient ingredient);
    List<Ingredient> getByName(String name);
    Map<Ingredient, String> getIngredientsByRecipeId(int id);
    Map<Ingredient, String> addToRelationTable(int recipeID, Map<Ingredient, String> ingredients);
    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);

    Product getByCode(String code);

    void addToPantry(int productId, int zone);

    List<ShoppingListIngredient> getForShoppingList(int id);

    List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId);

}
