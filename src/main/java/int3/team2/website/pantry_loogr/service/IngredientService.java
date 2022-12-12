package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.*;

import java.util.List;
import java.util.Map;

public interface IngredientService {
    Ingredient get(int ingredientID);
    List<Ingredient> getAll();
    List<Ingredient> add(Ingredient ingredient);
    List<Ingredient> getByName(String name);
    Map<Ingredient, Integer> getIngredientsByRecipeId(int id);
    Map<Ingredient, Integer> addToRelationTable(int recipeID, Map<Ingredient, Integer> ingredients);
    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);
    Product getByCode(String code);
    void addToPantry(int productId, int zone);
    Map<Ingredient, Integer> getForShoppingList(int id);
    List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId);
    List<Ingredient> getIngredientsByUser(int userID);
    void editPantryZoneProductQuantity(int pantryId, int productId, int quantity);
    void editPantryZoneProductAmountUsed(int pantryId, int productId, double percentage);
    void addShoppingListIngredients(int shoppingListId,  Map<Ingredient, Integer> shoppingListIngredients);
    void removePantryZoneProductQuantity(int pantryId, int productId, int quantityToRemove);
    void clearShoppingListIngredients(int shopping_list_id);
}
