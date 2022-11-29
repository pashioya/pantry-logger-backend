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
    Map<Ingredient, String> findIngredientsByRecipeId(int id);
    Map<Ingredient, String> addToRelationTable(int recipeID, Map<Ingredient, String> ingredients);

    List<PantryZoneProduct> getByPantryZoneId(int pantryZoneId);

    Product getByCode(String code);

    void addToPantry(int productId, int zone);
}
