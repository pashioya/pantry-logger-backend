package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Recipe;

import java.util.List;

public interface IngredientService {
    List<Ingredient> getAll();
    Ingredient get(int ingredientID);
    List<Ingredient> add(Ingredient ingredient);

}
