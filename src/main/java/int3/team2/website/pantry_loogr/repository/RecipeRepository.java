package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;

import java.util.List;

public interface RecipeRepository {
    List<Recipe> findByName(String recipe_name);
    List<Recipe> findByDifficulty(Difficulty recipe_difficulty);
    List<Recipe> findByTime(Time recipe_time);
    List<Recipe> findAll();
    List<Recipe> getRecipeByIngredient(List<Ingredient> ingredients);
    Recipe get(int id);
    Recipe createRecipe(Recipe recipe);
}
