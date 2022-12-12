package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.Product;

import java.util.List;
import java.util.Map;

public interface IngredientRepository {

    Ingredient get(int id);
    List<Ingredient> findAll();
    List<Ingredient> findByName(String name);
    List<Ingredient> findIngredientsByUser(int userID);
    Map<Ingredient, String> findIngredientsByRecipeId(int id);
    Map<Ingredient, String> addToRelationTable(int recipeID, Map<Ingredient, String> ingredients);
    Product getByCode(String code);
    void addToPantry(int productId, int zone);

    Map<Ingredient, Integer> getForShoppingList(int shoppingListId);

    PantryZoneProduct getPantryZoneProduct(int productId, int pantryId);
    void updatePantryZoneProduct(PantryZoneProduct product);
    void removePantryZoneProduct(PantryZoneProduct product);
    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);
    List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId);

    void addToShoppingListIngredients(int shoppingListId, Map<Ingredient, Integer> shoppingListIngredients);
    void clearShoppingListIngredients(int shopping_list_id);
}
