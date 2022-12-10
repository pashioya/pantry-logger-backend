package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.Product;
import int3.team2.website.pantry_loogr.domain.ShoppingListIngredient;

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
    PantryZoneProduct getPantryZoneProduct(int productId);
    void updatePantryZoneProduct(PantryZoneProduct product);
    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);
    List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId);
    List<ShoppingListIngredient> getForShoppingList(int shoppingListId);

}
