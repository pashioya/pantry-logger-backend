package int3.team2.website.pantry_loogr.presentation;

import int3.team2.website.pantry_loogr.domain.*;
import int3.team2.website.pantry_loogr.presentation.helper.DataItem;
import int3.team2.website.pantry_loogr.presentation.helper.HtmlItems;
import int3.team2.website.pantry_loogr.service.PantryZoneService;
import int3.team2.website.pantry_loogr.service.SensorDataService;
import int3.team2.website.pantry_loogr.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/pantry-zones")
public class PantryZoneController {

    UserService userService;
    PantryZoneService pantryZoneService;
    SensorDataService sensorDataService;

    public PantryZoneController(UserService userService, PantryZoneService pantryZoneService, SensorDataService sensorDataService) {
        this.userService = userService;
        this.pantryZoneService = pantryZoneService;
        this.sensorDataService = sensorDataService;
    }

    @GetMapping
    public String getAll(HttpSession httpSession, Model model) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Pantry Zones");
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

    @GetMapping("/{pantryZoneID}")
    public String pantryZoneDetails(HttpSession httpSession, Model model, @PathVariable int pantryZoneID) {
        EndUser user = userService.authenticate((String) httpSession.getAttribute("username"), (String) httpSession.getAttribute("password"));
        if(user == null) {
            return "redirect:/login";
        }
        PantryZone pantryZone = pantryZoneService.get(pantryZoneID);

        System.out.println(pantryZone.getProducts());

        model.addAttribute("pantryZone", pantryZone);
        model.addAttribute("products", pantryZone.getProducts());

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