package int3.team2.website.pantry_loogr.domain;

import java.util.*;

import int3.team2.website.pantry_loogr.presentation.RecipeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecipeRecommender {
    private static Logger logger = LoggerFactory.getLogger(RecipeRecommender.class);

    public static List<Recipe> filter(List<Recipe> recipes, List<Ingredient> ingredientsInPantry) {
        List<String> ingredients = new ArrayList<>();
        List<String> ingredientsInPantryNames = new ArrayList<>();
        ingredientsInPantry.forEach(i -> ingredientsInPantryNames.add(i.getName()));

        Map<Integer, Recipe> points = new HashMap<>();
        int recipePoints;
        for (Recipe recipe : recipes) {
            recipe.getIngredients().forEach(i -> ingredients.add(i.getName()));
            recipePoints = 0;
            for (String i : ingredients) {
                logger.debug(i);
                if (ingredientsInPantryNames.contains(i)) {
                    recipePoints += 10;
                    logger.debug(String.valueOf(recipePoints));
                }
            }
            points.put(recipePoints, recipe);
            ingredients.clear();
        }
        TreeMap<Integer, Recipe> sorted = new TreeMap<>(Collections.reverseOrder());
        sorted.putAll(points);

        return new ArrayList<>(sorted.values());
    }
}
