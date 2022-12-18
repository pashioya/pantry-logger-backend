package int3.team2.website.pantry_loogr.domain;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The RecipeRecommender is a static class that uses information like ingredients in the pantry and the user's preference to give recipe recommendation
 */
public class RecipeRecommender {
    private static Logger logger = LoggerFactory.getLogger(RecipeRecommender.class);

    /**
     * this function will return a sorted list of recipes (based on user's preference and the ingredients available)
     * @param recipes the list of recipes teh AI has to start off with,
     *                it will remove the ones that don't correspond with the users preference and sort them
     * @param ingredientsInPantry a list of Ingredients in the user's pantry
     * @param user used to get the user's preference
     * @return a sorted list of Recipes
     */
    public static List<Recipe> filter(List<Recipe> recipes, List<Ingredient> ingredientsInPantry, EndUser user) {
        recipes.removeIf(recipe -> getTagPoints(recipe, user) == -1);
        Map<Integer, Recipe> points = new HashMap<>();
        recipes.forEach(recipe -> points.put(countIngredients(recipe, ingredientsInPantry) + getTagPoints(recipe, user), recipe));

        TreeMap<Integer, Recipe> sorted = new TreeMap<>(Collections.reverseOrder());
        sorted.putAll(points);

        return new ArrayList<>(sorted.values());
    }

    /**
     * Will go through the tags tied to a recipe, if a tag is in the user.dislikes (the user doesn't like it),
     * it returns -1 and the recipe will be discarded. On the other hand, if a tag is in the user.likes,
     * it will have a higher chance of being recommended
     * @param recipe the current recipe we are analyzing
     * @param user the user we are basing our recommendation on
     * @return points related to how many recipe.tags are matching with user.likes
     */
    public static int getTagPoints(Recipe recipe, EndUser user) {
        int points = 0;
        for (Tag tag: user.getDislikes()) {
            if (recipe.getTags().contains(tag)) {return -1;}
        }

        for (Tag tag: user.getLikes()) {
            if (recipe.getTags().contains(tag)) {points += 10;}
        }

        return points;
    }

    /**
     * Will go through all the ingredients needed to cook a recipe.
     * it checks how many of those the user has stored in his pantry and assigns points for it.
     * @param recipe the recipe we are analyzing
     * @param ingredientsInPantry a list of ingredient the user has in his pantry
     * @return points according to how many of the ingredients required are owned by the user
     */
    private static int countIngredients(Recipe recipe, List<Ingredient> ingredientsInPantry) {
        int recipePoints = 0;
        for (Ingredient i : recipe.getIngredients().keySet()) {
            if (ingredientsInPantry.contains(i)) {
                recipePoints += 100/recipe.getIngredients().size();
                // if the user owns that ingredient it will add a number of points relative to the number of ingredients needed for the recipe
            } else {
                recipePoints -= 50/recipe.getIngredients().size();
                // if the user doesn't own that ingredient it will subtract a number of points relative to the number of ingredients needed for the recipe
            }
        }
        return  recipePoints;
    }

    /**
     * Function used to be used for displaying the Recipes.
     * @param filteredRecipes the sorted list of recipes
     * @param ingredientsInPantry a list of ingredient the user has in his pantry
     * @return a map. Each recipe is tied to a list of size 2.
     * The first element of that list is a list of ingredients that the user needs to cook the recipe
     * The second element of that list is a list of ingredients that the user has that is needed to cook the recipe
     */
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
