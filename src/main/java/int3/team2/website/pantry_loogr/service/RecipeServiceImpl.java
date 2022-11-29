package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;
import int3.team2.website.pantry_loogr.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeServiceImpl implements RecipeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
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
    public Recipe add(Recipe recipe) {
        Recipe newRecipe = recipeRepository.createRecipe(recipe);
        ingredientService.addToRelationTable(newRecipe.getId(), newRecipe.getIngredients());
        return newRecipe;
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
