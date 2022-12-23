package int3.team2.website.pantry_loogr.presentation;

import com.google.gson.Gson;
import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.domain.SensorData;
import int3.team2.website.pantry_loogr.domain.SensorType;
import int3.team2.website.pantry_loogr.service.PantryZoneService;
import int3.team2.website.pantry_loogr.service.SensorDataService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * allows the user to see the temperature, humidity and brightness in each of the places where he has put a sensor
 */
@RestController
@RequestMapping("/data/")
public class SensorDataApiControler {
    PantryZoneService pantryZoneService;
    SensorDataService sensorDataService;

    public SensorDataApiControler(PantryZoneService pantryZoneService, SensorDataService sensorDataService) {
        this.pantryZoneService = pantryZoneService;
        this.sensorDataService = sensorDataService;
    }
    @GetMapping("/{sensorBoxCode}/temp/{temp}/hum/{hum}/bright/{bright}")
    public String insertData(@PathVariable String sensorBoxCode, @PathVariable int temp, @PathVariable int hum, @PathVariable int bright) {
        PantryZone pantryZone = pantryZoneService.getBySensorBoxCode(sensorBoxCode);
        if(pantryZone == null) {
            return "Pantry Zone Not Found";
        }

        LocalDateTime dateTime = LocalDateTime.now();

        sensorDataService.add(new SensorData(dateTime, SensorType.valueOf("TEMPERATURE"), temp), pantryZone.getId());
        sensorDataService.add(new SensorData(dateTime, SensorType.valueOf("HUMIDITY"), hum), pantryZone.getId());
        sensorDataService.add(new SensorData(dateTime, SensorType.valueOf("BRIGHTNESS"), bright), pantryZone.getId());

        return "success";
    }

    @GetMapping("/{pantryZoneID}")
    public String checkData(@PathVariable int pantryZoneID) {
        PantryZone pantryZone = pantryZoneService.get(pantryZoneID);
        if(pantryZone == null) {
            return "Pantry Zone Not Found";
        }
        List<SensorData> sensorData = sensorDataService.getByPantryZoneBetween(pantryZone.getId(), LocalDateTime.of(2022, 10, 10, 0, 0), LocalDateTime.now());


        Gson gson = new Gson();
        String json = gson.toJson(sensorData);

        return json;
    }
}
