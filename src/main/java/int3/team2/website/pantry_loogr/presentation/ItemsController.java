package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.*;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import int3.team2.website.pantry_loogr.service.IngredientService;
import int3.team2.website.pantry_loogr.service.PantryZoneService;
import int3.team2.website.pantry_loogr.service.SensorDataService;
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

/**
 * Shows all the products that the user has or all the products in one of the user's pantryZones
 */
@Controller
@RequestMapping("/items")
public class ItemsController {
    private Logger logger;
    private UserService userService;
    private PantryZoneService pantryZoneService;
    private IngredientService ingredientService;
    private SensorDataService sensorDataService;

    public ItemsController(UserService userService, IngredientService ingredientService, PantryZoneService pantryZoneService, SensorDataService sensorDataService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.userService = userService;
        this.pantryZoneService = pantryZoneService;
        this.ingredientService = ingredientService;
        this.sensorDataService = sensorDataService;
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
        model.addAttribute("leftFooterList", new ArrayList<>(List.of(
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

    /**
     * Allows the user to change the amount contained in a product using the product and its location. i.e. if the user previously had a whole pack of spaghetti but used half, he can indicate that he used 50% of the pack and that information will be updated in the DB
     * @param httpSession used to identify the user
     * @param pantryId used to know the location of the product
     * @param productId used to identify which product the user wants to change
     * @param percentage the percentage that is remaining
     * @return redirects the user back to the items section
     */
    @GetMapping("/editItem/{pantryId}/{productId}/{percentage}")
    public String editItem(HttpSession httpSession,@PathVariable int pantryId, @PathVariable int productId, @PathVariable double percentage) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        if(!user.ownsPantry(pantryId)) {
            if (percentage == 0) {
                ingredientService.removePantryZoneProductQuantity(pantryId, productId, 1);
                return "redirect:/items";
            }
        }
        ingredientService.editPantryZoneProductAmountUsed(pantryId, productId, percentage);
        return "redirect:/items";
    }

    /**
     * Allows the user to edit the quantity of a product. i.e. if the user has a pack of spaghetti and buys another one, he can input 2 and that information will be updated in the DB
     * @param httpSession used to identify the user
     * @param editData the new information about of the product that the user wants to update (id, location and new amount)
     * @return redirects the user back to the items section
     */
    @RequestMapping(
            value="/editItem",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String editItemQuantity(HttpSession httpSession, @RequestBody MultiValueMap<String, String> editData) {
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
        model.addAttribute("leftFooterList", new ArrayList<>(List.of(
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
        model.addAttribute("leftFooterList", new ArrayList<>(List.of(
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

    @RequestMapping(
            value="/addPantryZone",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String addPantryZone(HttpSession httpSession, @RequestBody MultiValueMap<String, String> pantryZoneData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        logger.debug(pantryZoneData.toString());

        pantryZoneService.add(new PantryZone(
                pantryZoneData.get("newPantryZoneName").get(0),
                user.getId(),0,0,0,0,0,0
        ));
        return "redirect:pantry-zones";
    }


}
