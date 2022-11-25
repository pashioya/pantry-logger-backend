package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;

import java.util.List;

public interface IngredientRepository {
    List<Ingredient> findAll();
    Ingredient get(int id);
    List<Ingredient> findByName(String name);
    List<Ingredient> findIngredientsByRecipeId(int id);

}
