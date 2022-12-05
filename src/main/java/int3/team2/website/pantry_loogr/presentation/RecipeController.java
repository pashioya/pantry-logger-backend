package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.*;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import int3.team2.website.pantry_loogr.service.IngredientService;
import int3.team2.website.pantry_loogr.service.RecipeService;
import int3.team2.website.pantry_loogr.service.TagService;
import int3.team2.website.pantry_loogr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private Logger logger;
    private RecipeService recipeService;
    private IngredientService ingredientService;
    private TagService tagService;
    private UserService userService;

    public RecipeController(
            RecipeService recipeService,
            IngredientService ingredientService,
            TagService tagService,
            UserService userService
    ) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @GetMapping
    public String browser(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Browser");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON,"/recipes/recommend"),
                new DataItem(HtmlItems.HEADER_TITLE, "Browser"),
                new DataItem(HtmlItems.SEARCH_CONTAINER)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.SHOPPINGLIST),
                new DataItem(HtmlItems.CREATE_RECIPE)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>());

        model.addAttribute("recipes", recipeService.getAll());
        return "browser";
    }

    @GetMapping("/browser/{recipeID}")
    public String getRecipe(HttpSession httpSession, Model model, @PathVariable int recipeID) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        Recipe recipe = recipeService.get(recipeID);
        model.addAttribute("title", recipe.getName());
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON, "/recipes"),
                new DataItem(HtmlItems.HEADER_TITLE, recipe.getName()),
                new DataItem(HtmlItems.LOGO)
        )));
        model.addAttribute("recipe", recipe);
        return "recipe";
    }

    @GetMapping("/createrecipe")
    public String createRecipe(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Create Recipe");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON, "/recipes"),
                new DataItem(HtmlItems.HEADER_TITLE, "Create Recipe"),
                new DataItem(HtmlItems.LOGO)
        )));

        model.addAttribute("ingredients", ingredientService.getAll());
        return "createrecipe";
    }

    @RequestMapping(
            value="/createrecipe",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String createRecipe(HttpSession httpSession, @RequestBody MultiValueMap<String, String> recipeData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        logger.debug(recipeData.toString());
        Map<Ingredient, String> ingredients = new HashMap<>();
        List<String> ingTypes = recipeData.get("ingredient-types");
        List<String> ingAmounts = recipeData.get("ingredient-amounts");
        if (ingTypes.size() != ingAmounts.size()) {
            logger.error("Ingredient types and ingredient amounts are not of equal size!");
        }
        for(int i = 0; i < ingTypes.size(); i++) {
            Ingredient current =  ingredientService.get(Integer.parseInt(ingTypes.get(i)));
            ingredients.put(current, ingAmounts.get(i));
        }
        Recipe newRecipe = new Recipe(
                recipeData.get("recipe-name").get(0),
                Difficulty.valueOf(recipeData.get("recipe-difficulty").get(0)),
                recipeData.get("recipe-description").get(0),
                recipeData.get("cooking-step").stream().reduce((a, b) -> a + "<br><br>" + b).orElse(""),
                Time.valueOf(recipeData.get("recipe-time").get(0))
        );
        newRecipe.setIngredients(ingredients);
        recipeService.add(newRecipe);
        return "redirect:/browser";
    }

    @GetMapping("/recommend")
    public String recommendations(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title",   "Recommendations");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON,"/items"),
                new DataItem(HtmlItems.HEADER_TITLE, "Recommendations"),
                new DataItem(HtmlItems.LOGO)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.RECIPE_BROWSER)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.REFRESH),
                new DataItem(HtmlItems.CREATE_RECIPE)
        )));


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
    public String getRecipeByName(HttpSession httpSession, Model model, @PathVariable String name) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        List<Recipe> recipes = recipeService.getByName(name);
        logger.debug(String.valueOf(ingredientService.getByName("cucumber").size()));

        model.addAttribute("recipes", recipes);

        return "recipes";
    }
}
