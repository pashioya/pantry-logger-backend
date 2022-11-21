package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;
import int3.team2.website.pantry_loogr.repository.RecipeRepository;
import int3.team2.website.pantry_loogr.repository.RecipeRepositoryImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe get(int recipeID) {
        return recipeRepository.get(recipeID);
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
