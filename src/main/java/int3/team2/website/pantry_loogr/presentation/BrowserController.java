package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.EndUser;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;

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
                new DataItem(HtmlItems.SEARCH_CONTAINER),
                new DataItem(HtmlItems.DROPDOWN)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.SHOPPINGLIST),
                new DataItem(HtmlItems.CREATE_RECIPE)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>());

        model.addAttribute("recipes", recipeService.getAll());
        return "browser";
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
        return "redirect:/createrecipe";
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
