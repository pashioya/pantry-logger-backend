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
@RequestMapping("/items")
public class ItemsController {

    @GetMapping
    public String items(Model model) {
        model.addAttribute("title", "Items");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.HEADER_TITLES),
                new DataItem(HtmlItems.SEARCH_CONTAINER)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.RECOMMENDATIONS)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.SHOPPINGLIST),
                new DataItem(HtmlItems.SCANNER)
        )));
        return "items";
    }

    @GetMapping("/areas")
    public String areas(Model model) {
        model.addAttribute("title", "Areas");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.HEADER_TITLES),
                new DataItem(HtmlItems.SEARCH_CONTAINER)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.RECOMMENDATIONS)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.SHOPPINGLIST),
                new DataItem(HtmlItems.SCANNER)
        )));
        return "areas";
    }

    @GetMapping("/shoppinglist")
    public String shoppinglist(Model model) {
        model.addAttribute("title", "Shopping List");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.BACK_BUTTON),
                new DataItem(HtmlItems.HEADER_TITLE, "Shopping List"),
                new DataItem(HtmlItems.SEARCH_CONTAINER)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.SCANNER)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.RECOMMENDATIONS)
        )));
        return "shoppinglist";
    }

}
