package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/browser")
public class BrowserController {

    @GetMapping("/createrecipe")
    public String getCreateRecipe(Model model) {
        model.addAttribute("title", "Create Recipe");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.RECOMMENDATIONS),
                new DataItem(HtmlItems.HEADER_TITLE, "Create Recipe"),
                new DataItem(HtmlItems.LOGO)
        )));
        return "createrecipe";
    }

    @GetMapping("/recommendations")
    public String getRaccomandations(Model model) {
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
        return "reccomandations";
    }
}
