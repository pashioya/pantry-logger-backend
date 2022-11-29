package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import int3.team2.website.pantry_loogr.service.PantryZoneService;
import int3.team2.website.pantry_loogr.service.SensorDataService;
import int3.team2.website.pantry_loogr.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemsController {

    UserService userService;
    PantryZoneService pantryZoneService;

    public ItemsController(UserService userService, PantryZoneService pantryZoneService) {
        this.userService = userService;
        this.pantryZoneService = pantryZoneService;
    }

    @GetMapping
    public String items(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user != null) {
            return "redirect:/pantry-zones";
        }
        model.addAttribute("title", "Items");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.HEADER_TITLES),
                new DataItem(HtmlItems.SEARCH_CONTAINER)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.RECOMMENDATIONS)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>(Arrays.asList(
//                new DataItem(HtmlItems.SHOPPINGLIST),
                new DataItem(HtmlItems.SCANNER)
        )));

        List<HashMap<String, String>> products = pantryZoneService.getAllForUser();

        model.addAttribute("products", products);

        model.addAttribute("itemsActive", "selected");
        model.addAttribute("pantryZoneActive", "undefined");

        return "items";
    }

    @GetMapping("/areas")
    public String areas(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user != null) {
            return "redirect:/pantry-zones";
        }
        model.addAttribute("title", "Areas");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.HEADER_TITLES),
                new DataItem(HtmlItems.SEARCH_CONTAINER)
        )));
        model.addAttribute("leftFooterList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.RECOMMENDATIONS)
        )));
        model.addAttribute("rightFooterList", new ArrayList<>(Arrays.asList(
//                new DataItem(HtmlItems.SHOPPINGLIST),
                new DataItem(HtmlItems.SCANNER)
        )));
        model.addAttribute("areasActive", "selected");
        model.addAttribute("itemsActive", "undefined");
        return "areas";
    }

    @GetMapping("/shoppinglist")
    public String shoppinglist(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user != null) {
            return "redirect:/pantry-zones";
        }
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
