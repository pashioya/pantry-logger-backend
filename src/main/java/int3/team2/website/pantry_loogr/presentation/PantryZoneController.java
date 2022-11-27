package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.*;
import int3.team2.website.pantry_loogr.service.IngredientService;
import int3.team2.website.pantry_loogr.service.PantryZoneService;
import int3.team2.website.pantry_loogr.service.RecipeService;
import int3.team2.website.pantry_loogr.service.SensorDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/pantry-zones")
public class PantryZoneController {

    PantryZoneService pantryZoneService;
    SensorDataService sensorDataService;

    public PantryZoneController(PantryZoneService pantryZoneService, SensorDataService sensorDataService) {
        this.pantryZoneService = pantryZoneService;
        this.sensorDataService = sensorDataService;
    }

    @GetMapping
    public String getAllRecipe(Model model) {
        List<PantryZone> pantryZones = pantryZoneService.getAll();

        model.addAttribute("pantryZones", pantryZones);
        return "pantryZones";
    }

    @GetMapping("/{pantryZoneID}")
    public String pantryZoneDetails(Model model, @PathVariable int pantryZoneID) {
        PantryZone pantryZone = pantryZoneService.get(pantryZoneID);
        //List<SensorData> sensorData = sensorDataService.getByPantryZoneBetween(pantryZoneID, LocalDateTime.of(2022, 11,23, 0, 01, 00), LocalDateTime.of(2022, 11,23, 5, 01, 00));
        List<SensorData> sensorData = sensorDataService.getByPantryZone(pantryZoneID);


        model.addAttribute("pantryZone", pantryZone);
        model.addAttribute("sensorData", sensorData);

        return "PantryZoneDetails";
    }
}