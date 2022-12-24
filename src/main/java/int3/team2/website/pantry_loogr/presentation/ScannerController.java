package int3.team2.website.pantry_loogr.presentation;


import int3.team2.website.pantry_loogr.domain.*;
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
import java.time.LocalDate;
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
        List<PantryZone> pantryZones = pantryZoneService.getAllForUser(user.getId());
        model.addAttribute("pantryZones", pantryZones);
        model.addAttribute("title", "Scanner");
        model.addAttribute("ingredients", ingredientService.getAll());


        model.addAttribute("singleIngredientsList", ingredientService.getUnpackagedProducts());
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

        Product product;

        if (registerData.keySet().contains("create") && registerData.get("create").get(0).equals("on")) {
            Ingredient ingredient = ingredientService.get(Integer.parseInt(registerData.get("ingredient").get(0)));

            product = ingredientService.addProduct(new Product(
                    Integer.parseInt(registerData.get("ingredient").get(0)),
                    ingredient.getName(),
                    registerData.get("name").get(0),
                    registerData.get("code").get(0),
                    Integer.parseInt(registerData.get("amount").get(0)),
                    ingredient.getImagePath()
            ));
        } else {
            product = ingredientService.getProduct(Integer.parseInt(registerData.getFirst("productId")));
        }
        int quantity = Integer.parseInt(registerData.get("quantity").get(0));

        ingredientService.addToPantry(
                new PantryZoneProduct(
                        product,
                        quantity,
                        0,
                        LocalDate.now()
                ),
                Integer.parseInt(registerData.getFirst("zone"))
        );
        return "redirect:/scanner";
    }

    /**
     * checks if a product already exists in the DB, it does so by using the code (unique for every product in Europe)
     */
    @RequestMapping(
            value = "/checkForProduct",
            method= RequestMethod.GET,
            produces="application/json"
    )
    public @ResponseBody Map<String, String> checkForProduct(HttpSession httpSession, @RequestParam("code") String code) {
        Map<String, String> map = new HashMap<>();
        map.put("found", "false");
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return map;
        }

        Product product = ingredientService.getByCode(code);
        if(product != null && product.getId() > 0) {
            map.put("found", "true");
            map.put("productId", String.valueOf(product.getProductId()));
            map.put("name", product.getProductName());
            map.put("ingredientId", String.valueOf(product.getId()));
            map.put("amount", String.valueOf(product.getSize()));
        }
        return map;
    }

    @RequestMapping(
            value = "/checkForUnpackagedProduct",
            method= RequestMethod.GET,
            produces="application/json"
    )
    public @ResponseBody Map<String, String> checkForUnpackagedProduct(HttpSession httpSession, @RequestParam("productId") int id) {
        Map<String, String> map = new HashMap<>();
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return map;
        }
        map.put("found", "false");

        List<Product> list = ingredientService.getUnpackagedProducts();
        Product product = list.stream().filter(x -> x.getProductId() == id).findFirst().orElse(null);

        if (product != null) {
            map.put("found", "true");
            map.put("ingredientId", String.valueOf(product.getId()));
            map.put("name", product.getProductName());
            map.put("amount", String.valueOf(product.getSize()));
            map.put("productId", String.valueOf(product.getProductId()));
        }
        return map;
    }
}
