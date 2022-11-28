package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.*;
import int3.team2.website.pantry_loogr.service.IngredientService;
import int3.team2.website.pantry_loogr.service.RecipeService;
import int3.team2.website.pantry_loogr.service.TagService;
import int3.team2.website.pantry_loogr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Controller
@RequestMapping("/recipes")
public class RecipeController {
    private Logger logger = LoggerFactory.getLogger(RecipeController.class);

    RecipeService recipeService;
    IngredientService ingredientService;
    TagService tagService;

    UserService userService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService, TagService tagService, UserService userService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.tagService = tagService;
        this.userService = userService;
    }
    // @RequestParam(required = false) String recipe_name,
    @GetMapping
    public String getAllRecipe(Model model) {
        List<Recipe> recipes;
        recipes = recipeService.getAll();

        model.addAttribute("recipes", recipes);
        return "recipes";
    }

    @GetMapping("/{recipeID}")
    public String recipeDetails(Model model, @PathVariable int recipeID) {
        Recipe recipe = recipeService.get(recipeID);
        model.addAttribute("recipe", recipe);

        return "recipeDetails";
    }

    @GetMapping("/recommend")
    public String getRecipeRecommendation(Model model) {
        List<Recipe> recipes = recipeService.getAll();
        recipes.replaceAll(recipe -> recipeService.get(recipe.getId()));
        List<Ingredient> ingredientsInPantry = new ArrayList<>();
        Collections.addAll(ingredientsInPantry,
                new Ingredient("onion"), new Ingredient("garlic"), new Ingredient("ground beef"),
                new Ingredient("tomato"), new Ingredient("paprika"), new Ingredient("sour cream"),
                new Ingredient("carrot"), new Ingredient("spaghetti"), new Ingredient("egg"),
                new Ingredient("bacon"), new Ingredient("butter"), new Ingredient("milk"));

        List<Recipe> recommendations = RecipeRecommender.filter(recipes, ingredientsInPantry);
        recommendations.forEach(r -> logger.debug(r.toString()));

        model.addAttribute("recommendations", recommendations);
        return "recommendations";
    }

    @GetMapping("/search/{name}")
    public String getRecipeByName(Model model, @PathVariable String name) {
        List<Recipe> recipes = recipeService.getByName(name);
        logger.debug(String.valueOf(ingredientService.getByName("cucumber").size()));

        model.addAttribute("recipes", recipes);

        return "recipes";
    }

    @GetMapping("/ingredients")
    public String getAllIngredient(Model model) {
        List<Ingredient> ingredients;
        ingredients = ingredientService.getAll();

        model.addAttribute("ingredients", ingredients);
        return "ingredients";
    }

    @GetMapping("/tags")
    public String getAllTags(Model model) {
        List<Tag> tags;
        tags = tagService.getAll();

        model.addAttribute("tags", tags);
        return "tags";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<EndUser> users;
        users = userService.getAll();

        model.addAttribute("users", users);
        return "users";
    }
}