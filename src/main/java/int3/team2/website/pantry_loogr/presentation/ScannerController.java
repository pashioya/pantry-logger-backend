package int3.team2.website.pantry_loogr.presentation;


import int3.team2.website.pantry_loogr.domain.Product;
import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.service.IngredientService;
import int3.team2.website.pantry_loogr.service.PantryZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/scanner")
public class ScannerController {

    private Logger logger;
    PantryZoneService pantryZoneService;
    IngredientService ingredientService;

    public ScannerController(PantryZoneService pantryZoneService, IngredientService ingredientService) {
        logger = LoggerFactory.getLogger(this.getClass());
        this.pantryZoneService = pantryZoneService;
        this.ingredientService = ingredientService;
    }


    @GetMapping
    public String scanner(Model model) {
        List<PantryZone> pantryZones = pantryZoneService.getAll();
        model.addAttribute("pantryZones", pantryZones);
        model.addAttribute("title", "Pantry Logr: Scanner");
        return "scanner";
    }

    @RequestMapping(
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String addItem(@RequestBody MultiValueMap<String, String> registerData) {
        logger.debug(registerData.toString());
        logger.debug(registerData.getFirst("item_id"));
        logger.debug(registerData.getFirst("zone"));
        ingredientService.addToPantry(Integer.parseInt(registerData.getFirst("item_id")), Integer.parseInt(registerData.getFirst("zone")));
        return "redirect:/scanner";
    }

    @RequestMapping(
            value = "/checkForItem",
            method= RequestMethod.GET,
            produces="application/json"
    )
    public @ResponseBody Map<String, String> checkForItem(@RequestParam("code") String code) {
        logger.debug("code: " + code);
        Map<String, String> map = new HashMap<>();
        Product item = ingredientService.getByCode(code);


        map.put("found", "false");
        if(item.getId() > 0) {
            map.put("found", "true");
            logger.debug(String.format("found item: %s, (%d)",item.getName(), item.getSize()));

            //TODO replace with actual filling code
            map.put("itemId", String.valueOf(item.getId()));
            map.put("name", item.getName());
            map.put("amount", String.valueOf(item.getSize()));
        }
        return map;
    }
}
