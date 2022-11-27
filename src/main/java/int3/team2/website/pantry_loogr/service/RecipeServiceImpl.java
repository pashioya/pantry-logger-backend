package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;
import int3.team2.website.pantry_loogr.repository.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;
    private IngredientService ingredientService;

    public RecipeServiceImpl(RecipeRepository recipeRepository, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe get(int recipeID) {
        Recipe recipe = recipeRepository.get(recipeID);
        recipe.setIngredients(ingredientService.getIngredientsByRecipeId(recipe.getId()));
        return recipe;
    }

    @Override
    public List<Recipe> add(Recipe recipe) {
        return null;
    }

    @Override
    public List<Recipe> getByName(String name) {
        return recipeRepository.findByName(name);
    }

    @Override
    public List<Recipe> getByDifficulty(Difficulty difficulty) {
        return recipeRepository.findByDifficulty(difficulty);
    }

    @Override
    public List<Recipe> getByTime(Time time) {
        return recipeRepository.findByTime(time);
    }
}
