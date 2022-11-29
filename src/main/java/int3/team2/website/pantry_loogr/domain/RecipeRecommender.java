package int3.team2.website.pantry_loogr.domain;

import java.util.*;
import java.util.stream.Collectors;

import int3.team2.website.pantry_loogr.presentation.RecipeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecipeRecommender {
    private static Logger logger = LoggerFactory.getLogger(RecipeRecommender.class);

    public static List<Recipe> filter(List<Recipe> recipes, List<Ingredient> ingredientsInPantry) {
        List<String> ingredientsInPantryNames = ingredientsInPantry.stream().map(Ingredient::getName).toList();

        Map<Integer, Recipe> points = new HashMap<>();
        int recipePoints;
        for (Recipe recipe : recipes) {
            recipePoints = 0;
            for (Ingredient i : recipe.getIngredients().keySet()) {
                if (ingredientsInPantryNames.contains(i.getName())) {
                    recipePoints += 10;
                }
            }
            points.put(recipePoints, recipe);
        }
        TreeMap<Integer, Recipe> sorted = new TreeMap<>(Collections.reverseOrder());
        sorted.putAll(points);

        return new ArrayList<>(sorted.values());
    }
}
