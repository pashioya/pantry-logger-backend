package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    // @RequestParam(required = false) String recipe_name,
    @GetMapping
    public String getAllRecipe(Model model) {
        List<Recipe> recipes;
        recipes = recipeService.getAll();

        model.addAttribute("recipes", recipes);
        return "recipes";
    }
}