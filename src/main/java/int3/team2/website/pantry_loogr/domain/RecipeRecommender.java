package int3.team2.website.pantry_loogr.domain;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecipeRecommender {
    private static Logger logger = LoggerFactory.getLogger(RecipeRecommender.class);

    public static List<Recipe> filter(List<Recipe> recipes, List<Ingredient> ingredientsInPantry) {
        Map<Integer, Recipe> points = new HashMap<>();
        for (Recipe recipe : recipes) {
            points.put(countIngredients(recipe, ingredientsInPantry), recipe);
        }
        TreeMap<Integer, Recipe> sorted = new TreeMap<>(Collections.reverseOrder());
        sorted.putAll(points);

        return new ArrayList<>(sorted.values());
    }

    private static int countIngredients(Recipe recipe, List<Ingredient> ingredientsInPantry) {
        int recipePoints = 0;
        for (Ingredient i : recipe.getIngredients().keySet()) {
            if (ingredientsInPantry.contains(i)) {
                recipePoints += 100/recipe.getIngredients().size();
            } else {
                recipePoints -= 50/recipe.getIngredients().size();
            }
        }
        return  recipePoints;
    }

    public static Map<Recipe, List<List<Ingredient>>> showIngredients(List<Recipe> filteredRecipes, List<Ingredient> ingredientsInPantry) {
        Map<Recipe, List<List<Ingredient>>> ingredientsDetails = new LinkedHashMap<>();

        filteredRecipes.forEach(recipe -> {
                    List<Ingredient> recipeIngredients = recipe.getIngredients().keySet().stream().toList();
                    List<Ingredient> ingredientsNeeded = new ArrayList<>();
                    List<Ingredient> ingredientsOwned = new ArrayList<>();

                    for (Ingredient ing : recipeIngredients) {
                        if (ingredientsInPantry.contains(ing)) {
                            ingredientsOwned.add(ing);
                        } else {
                            ingredientsNeeded.add(ing);
                        }
                    }
                    List<List<Ingredient>> ingredients = new ArrayList<>();

                    ingredients.add(ingredientsNeeded);
                    ingredients.add(ingredientsOwned);

                    ingredientsDetails.put(recipe, ingredients);
                }
        );
        return ingredientsDetails;
    }
}
