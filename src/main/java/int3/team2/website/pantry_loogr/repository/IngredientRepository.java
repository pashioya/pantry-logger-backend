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
    List<Ingredient> getProductsEnteredAWeekAgo(int userId);
    Map<Ingredient, Integer> findIngredientsByRecipeId(int id);
    Map<Ingredient, Integer> addToRelationTable(int recipeID, Map<Ingredient, Integer> ingredients);
    Product getProduct(int id);
    Product addProduct(Product product);
    List<Product> getByCode(String code);
    void addToPantry(PantryZoneProduct product);
    void updatePantryZoneProduct(PantryZoneProduct product);
    void removePantryZoneProduct(PantryZoneProduct product);
    PantryZoneProduct getPantryZoneProduct(int productId, int pantryId);
    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);
    List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId);
    void addToShoppingListIngredients(int shoppingListId, Map<Ingredient, Integer> shoppingListIngredients);
    void clearShoppingListIngredients(int shopping_list_id);
    Map<Ingredient, Integer> getForShoppingList(int shoppingListId);
}
