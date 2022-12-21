package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAll();
    Recipe get(int recipeID);
    Recipe add(Recipe recipe);
    List<Recipe> getByName(String name);
    List<Recipe> getByDifficulty(Difficulty difficulty);
    List<Recipe> getByTime(Time time);
    List<Recipe> getRecipeByIngredient(List<Ingredient> ingredients);
}
