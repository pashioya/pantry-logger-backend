package int3.team2.website.pantry_loogr.presentation;

import com.google.gson.Gson;
import int3.team2.website.pantry_loogr.domain.*;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public String getAll(Model model) {
        model.addAttribute("title", "Pantry Zones");
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

        model.addAttribute("itemsActive", "undefined");
        model.addAttribute("pantryZoneActive", "selected");

        List<PantryZone> pantryZones = pantryZoneService.getAll();

        model.addAttribute("pantryZones", pantryZones);
        List<SensorData> temp = new ArrayList<>();
        List<SensorData> hum = new ArrayList<>();
        List<SensorData> lum = new ArrayList<>();
//
//        pantryZones.forEach(x -> {
//            sensorDataService.getLatestByPantryZone(x.getId()).forEach(y -> {
//                switch (y.getType()) {
//                    case TEMPERATURE -> temp.add(y);
//                    case HUMIDITY -> hum.add(y);
//                    case BRIGHTNESS -> lum.add(y);
//                }
//            });
//        });

        return "pantryZones";
    }

    @GetMapping("/allItems")
    public String pantryZoneAllItems(Model model) {
        List<PantryZone> pantryZones = pantryZoneService.getAll();
        for (PantryZone pantryZone:pantryZones) {
            System.out.println(pantryZone.getItems());
        }
        //Object test = new Object[] {pantryZones.get(0).getItems().get(0)};


        model.addAttribute("pantryZones", pantryZones);

        return "items";
    }
    @GetMapping("/{pantryZoneID}")
    public String pantryZoneDetails(Model model, @PathVariable int pantryZoneID) {
        PantryZone pantryZone = pantryZoneService.get(pantryZoneID);

        System.out.println(pantryZone.getItems());

        model.addAttribute("pantryZone", pantryZone);
        model.addAttribute("items", pantryZone.getItems());

        return "PantryZoneDetails";
    }
    @GetMapping("/raw-data/{pantryZoneID}")
    public String checkData(Model model, @PathVariable int pantryZoneID) {
        PantryZone pantryZone = pantryZoneService.get(pantryZoneID);
        if(pantryZone == null) {
            return "Pantry Zone Not Found";
        }
        model.addAttribute("pantryZone", pantryZone);
        //model.addAttribute("sensorData", sensorDataService.getByPantryZoneBetween(pantryZone.getId(), LocalDateTime.of(2022, 10, 10, 0, 0), LocalDateTime.now()));
        model.addAttribute("sensorData", sensorDataService.getByPantryZone(pantryZone.getId()));

        return "pantryZoneRawData";
    }

}