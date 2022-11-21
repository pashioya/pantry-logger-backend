package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.repository.RecipeRepository;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAll();
    Recipe get(int recipeID);
    List<Recipe> add(Recipe recipe);
}
