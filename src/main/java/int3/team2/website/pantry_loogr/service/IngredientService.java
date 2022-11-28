package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;

import java.util.List;
import java.util.Map;

public interface IngredientService {
    List<Ingredient> getAll();
    Ingredient get(int ingredientID);
    List<Ingredient> add(Ingredient ingredient);
    List<Ingredient> getByName(String name);
    Map<Ingredient, String> getIngredientsByRecipeId(int id);
    Map<Ingredient, String> addToRelationTable(Map<Ingredient, String> ingredients);

}
