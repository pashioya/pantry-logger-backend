package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.service.IngredientService;
import int3.team2.website.pantry_loogr.service.RecipeService;
import int3.team2.website.pantry_loogr.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    RecipeService recipeService;
    IngredientService ingredientService;
    TagService tagService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService, TagService tagService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.tagService = tagService;
    }
    // @RequestParam(required = false) String recipe_name,
    @GetMapping
    public String getAllRecipe(Model model) {
        List<Recipe> recipes;
        recipes = recipeService.getAll();

        model.addAttribute("recipes", recipes);
        return "recipes";
    }

    @GetMapping("/recipes/{recipeID}")
    public String recipeDetails(Model model, @PathVariable int recipeID) {
        Recipe recipe = recipeService.get(recipeID);

        model.addAttribute("recipe", recipe);

        return "recipeDetails";
    }

    @GetMapping("/search/{name}")
    public String getRecipeByName(Model model, @PathVariable String name) {
        List<Recipe> recipes = recipeService.getByName(name);

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

}