package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.*;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import int3.team2.website.pantry_loogr.service.IngredientService;
import int3.team2.website.pantry_loogr.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/browser")
public class BrowserController {

    private Logger logger;
    private RecipeService recipeService;
    private IngredientService ingredientService;

    public BrowserController(RecipeService recipeService, IngredientService ingredientService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public String browser(Model model) {
        model.addAttribute("title", "Browser");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON),
                new DataItem(HtmlItems.HEADER_TITLE, "Browser"),
                new DataItem(HtmlItems.SEARCH_DROPDOWN)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
//                new DataItem(HtmlItems.SHOPPINGLIST),
                new DataItem(HtmlItems.CREATE_RECIPE)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>());

        model.addAttribute("recipes", recipeService.getAll());
        return "browser";
    }

    @GetMapping("/{recipeID}")
    public String getRecipe(Model model, @PathVariable int recipeID) {
        Recipe recipe = recipeService.get(recipeID);
        model.addAttribute("title", recipe.getName());
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON),
                new DataItem(HtmlItems.HEADER_TITLE, recipe.getName()),
                new DataItem(HtmlItems.LOGO)
        )));
        model.addAttribute("recipe", recipe);
        return "/recipe";
    }

    @GetMapping("/createrecipe")
    public String createRecipe(Model model) {
        model.addAttribute("title", "Create Recipe");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON),
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
    public String createRecipe(@RequestBody MultiValueMap<String, String> recipeData) {
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

    @GetMapping("/recommendations")
    public String recommendations(Model model) {
        model.addAttribute("title",   "Recommendations");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON),
                new DataItem(HtmlItems.HEADER_TITLE, "Recommendations"),
                new DataItem(HtmlItems.DROPDOWN)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.RECIPE_BROWSER)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.REFRESH),
                new DataItem(HtmlItems.CREATE_RECIPE)
        )));
        return "recommendations";
    }
}
