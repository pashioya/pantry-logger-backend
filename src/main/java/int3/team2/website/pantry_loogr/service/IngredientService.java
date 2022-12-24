package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.*;

import java.util.List;
import java.util.Map;

public interface IngredientService {
    Ingredient get(int ingredientID);
    List<Ingredient> getAll();
    List<Ingredient> add(Ingredient ingredient);
    List<Ingredient> getByName(String name);
    List<Ingredient> getIngredientsByUser(int userID);
    Map<Ingredient, Integer> getIngredientsByRecipeId(int id);
    Map<Ingredient, Integer> addToRelationTable(int recipeID, Map<Ingredient, Integer> ingredients);
    Product addProduct(Product product);
    Product getProduct(int productId);
    Product getByCode(String code);
    List<Product> getUnpackagedProducts();
    void addToPantry(PantryZoneProduct product, int pantryId);
    List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId);
    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);
    void removePantryZoneProductQuantity(int pantryId, int productId, int quantityToRemove);
    List<Ingredient> getProductsEnteredAWeekAgo(int userId);
    void editPantryZoneProductQuantity(int pantryId, int productId, int quantity);
    void editPantryZoneProductAmountUsed(int pantryId, int productId, double percentage);
    Map<Ingredient, Integer> getForShoppingList(int id);
    void addShoppingListIngredients(int shoppingListId,  Map<Ingredient, Integer> shoppingListIngredients);
    void clearShoppingListIngredients(int shopping_list_id);
}
