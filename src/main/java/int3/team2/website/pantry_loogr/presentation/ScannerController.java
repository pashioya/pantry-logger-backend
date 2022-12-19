package int3.team2.website.pantry_loogr.presentation;


import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.domain.Ingredient;
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
        model.addAttribute("ingredients", ingredientService.getAll());
        return "scanner";
    }

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

        if (registerData.get("create").get(0).equals("on")) {
            Ingredient ingredient = ingredientService.get(Integer.parseInt(registerData.get("ingredient").get(0)));

            Product product = ingredientService.addProduct(new Product(
                    Integer.parseInt(registerData.get("ingredient").get(0)),
                    ingredient.getName(),
                    registerData.get("name").get(0),
                    registerData.get("code").get(0),
                    Integer.parseInt(registerData.get("amount").get(0)),
                    ingredient.getImagePath()
            ));
            ingredientService.addToPantry(product.getProductId(), Integer.parseInt(registerData.getFirst("zone")));
        } else {
            ingredientService.addToPantry(Integer.parseInt(registerData.getFirst("productId")), Integer.parseInt(registerData.getFirst("zone")));

        }
        return "redirect:/scanner";
    }

    @RequestMapping(
            value = "/checkForItem",
            method= RequestMethod.GET,
            produces="application/json"
    )
    public @ResponseBody Map<String, String> checkForItem(HttpSession httpSession, @RequestParam("code") String code) {
        Map<String, String> map = new HashMap<>();
        map.put("found", "false");
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return map;
        }

        Product product = ingredientService.getByCode(code);
        if(product != null && product.getId() > 0) {
            map.put("found", "true");
            map.put("productId", String.valueOf(product.getId()));
            map.put("name", product.getName());
            map.put("amount", String.valueOf(product.getSize()));
        }
        return map;
    }


}
