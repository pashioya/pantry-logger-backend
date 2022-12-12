package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.Product;

import java.util.List;
import java.util.Map;

public interface IngredientRepository {
    List<Ingredient> findAll();
    Ingredient get(int id);
    List<Ingredient> findByName(String name);
    Map<Ingredient, Integer> findIngredientsByRecipeId(int id);
    Map<Ingredient, Integer> addToRelationTable(int recipeID, Map<Ingredient, Integer> ingredients);
    Product getByCode(String code);

    void addToPantry(int productId, int zone);

    Map<Ingredient, Integer> getForShoppingList(int shoppingListId);

    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);

    List<Ingredient> findIngredientsByUser(int userID);

    List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId);

    void addToShoppingListIngredients(int shoppingListId, Map<Ingredient, Integer> shoppingListIngredients);
    void clearShoppingListIngredients(int shopping_list_id);
}
