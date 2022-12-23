package int3.team2.website.pantry_loogr.presentation;


import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.domain.Product;
import int3.team2.website.pantry_loogr.domain.PantryZone;
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

/**
 * shows the page that allows the user to scan a product and to add it into his pantry
 */
@Controller
@RequestMapping("/scanner")
public class ScannerController {

    private Logger logger;
    UserService userService;
    PantryZoneService pantryZoneService;
    IngredientService ingredientService;

    public ScannerController(UserService userService, PantryZoneService pantryZoneService, IngredientService ingredientService) {
        this.userService = userService;
        logger = LoggerFactory.getLogger(this.getClass());
        this.pantryZoneService = pantryZoneService;
        this.ingredientService = ingredientService;
    }


    @GetMapping
    public String scanner(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        List<PantryZone> pantryZones = pantryZoneService.getAll();
        model.addAttribute("pantryZones", pantryZones);
        model.addAttribute("title", "Scanner");
        return "scanner";
    }

    /**
     * Allows the user to add a new product if it doesn't already exist in the DB
     */
    @RequestMapping(
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String addItem(HttpSession httpSession, @RequestBody MultiValueMap<String, String> registerData) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        logger.debug(registerData.toString());
        logger.debug(registerData.getFirst("item_id"));
        logger.debug(registerData.getFirst("zone"));
        ingredientService.addToPantry(Integer.parseInt(registerData.getFirst("item_id")), Integer.parseInt(registerData.getFirst("zone")));
        return "redirect:/scanner";
    }

    /**
     * checks if a product already exists in the DB, it does so by using the code (unique for every product in Europe)
     */
    @RequestMapping(
            value = "/checkForItem",
            method= RequestMethod.GET,
            produces="application/json"
    )
    public @ResponseBody Map<String, String> checkForItem(HttpSession httpSession, @RequestParam("code") String code) {
        logger.debug("code: " + code);
        Map<String, String> map = new HashMap<>();
        map.put("found", "false");
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return map;
        }
        Product product = ingredientService.getByCode(code);

        if(product.getId() > 0) {
            map.put("found", "true");
            logger.debug(String.format("found item: %s, (%d)",product.getProductName(), product.getSize()));

            //TODO replace with actual filling code
            map.put("itemId", String.valueOf(product.getId()));
            map.put("name", product.getName());
            map.put("amount", String.valueOf(product.getSize()));
        }
        return map;
    }
}
