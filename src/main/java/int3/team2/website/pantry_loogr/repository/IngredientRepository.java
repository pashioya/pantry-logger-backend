package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;

import java.util.List;
import java.util.Map;

public interface IngredientRepository {
    List<Ingredient> findAll();
    Ingredient get(int id);
    List<Ingredient> findByName(String name);
    Map<Ingredient, String> findIngredientsByRecipeId(int id);
    Map<Ingredient, String> addToRelationTable(Map<Ingredient, String> ingredients);

}
