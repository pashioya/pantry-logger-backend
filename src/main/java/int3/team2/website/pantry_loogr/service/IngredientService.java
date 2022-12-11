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
    Map<Ingredient, String> getIngredientsByRecipeId(int id);
    Map<Ingredient, String> addToRelationTable(int recipeID, Map<Ingredient, String> ingredients);
    Product getByCode(String code);
    void addToPantry(int productId, int zone);
    void editPantryZoneProductSize(int pantryId, int productId, double percentage);
    void editPantryZoneProductQuantity(int pantryId, int productId, int quantityToRemove);
    List<PantryZoneProduct> getProductsAndPantryZonesByUser(int userId);
    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);
    List<ShoppingListIngredient> getForShoppingList(int id);
}
