package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.*;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import int3.team2.website.pantry_loogr.service.IngredientService;
import int3.team2.website.pantry_loogr.service.PantryZoneService;
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
@RequestMapping("/items")
public class ItemsController {
    private Logger logger;

    private UserService userService;
    private PantryZoneService pantryZoneService;
    private IngredientService ingredientService;

    public ItemsController(UserService userService, IngredientService ingredientService, PantryZoneService pantryZoneService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.userService = userService;
        this.pantryZoneService = pantryZoneService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public String items(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Items");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.PROFILE),
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

        List<PantryZoneProduct> products = ingredientService.getProductsAndPantryZonesByUser(user.getId());

        model.addAttribute("products", products);

        model.addAttribute("itemsActive", "selected");
        model.addAttribute("pantryZoneActive", "undefined");
        return "items";
    }


    //TODO rework the way this is accessed
    @GetMapping("/editItem/{pantryId}/{productId}/{percentage}")
    public String editItem(HttpSession httpSession,@PathVariable int pantryId, @PathVariable int productId, @PathVariable double percentage) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        if (percentage == 0) {
            logger.debug(pantryId + " " + productId);
            ingredientService.removePantryZoneProductQuantity(pantryId, productId, 1);
            return "redirect:/items";
        }
        ingredientService.editPantryZoneProductAmountUsed(pantryId, productId, percentage);
        return "redirect:/items";
    }

    @RequestMapping(
            value="/editItem",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String loginUser(HttpSession httpSession, @RequestBody MultiValueMap<String, String> editData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        logger.debug(editData.toString());
        ingredientService.editPantryZoneProductQuantity(
                Integer.parseInt(editData.get("pantryId").get(0)),
                Integer.parseInt(editData.get("productId").get(0)),
                Integer.parseInt(editData.get("quantityChange").get(0))
        );
        return "redirect:/items";
    }

    @GetMapping("/pantry-zones")
    public String pantryZones(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Pantry Zones");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.PROFILE),
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

        model.addAttribute("itemsActive", "undefined");
        model.addAttribute("pantryZoneActive", "selected");

        List<PantryZone> pantryZones = pantryZoneService.getAllForUser(user.getId());

        model.addAttribute("pantryZones", pantryZones);
        List<SensorData> temp = new ArrayList<>();
        List<SensorData> hum = new ArrayList<>();
        List<SensorData> lum = new ArrayList<>();

        return "pantryZones";
    }

    @GetMapping("/pantry-zones/{pantryZoneID}")
    public String pantryZoneDetails(HttpSession httpSession, Model model, @PathVariable int pantryZoneID) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Items");
        model.addAttribute("headerList", new ArrayList<>(Arrays.asList(
                new DataItem(HtmlItems.PROFILE),
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

        List<PantryZoneProduct> products = ingredientService.getByPantryZoneId(pantryZoneID);

        model.addAttribute("products", products);

        model.addAttribute("itemsActive", "selected");
        model.addAttribute("pantryZoneActive", "undefined");

        return "items";
    }

}
