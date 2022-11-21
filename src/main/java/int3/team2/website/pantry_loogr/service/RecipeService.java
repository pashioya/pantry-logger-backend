package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.repository.RecipeRepository;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAll();
    List<Recipe> add(Recipe recipe);
}
