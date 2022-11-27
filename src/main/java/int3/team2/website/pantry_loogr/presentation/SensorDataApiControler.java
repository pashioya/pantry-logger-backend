package int3.team2.website.pantry_loogr.presentation;

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

@RestController
@RequestMapping("/data/")
public class SensorDataApiControler {
    PantryZoneService pantryZoneService;
    SensorDataService sensorDataService;

    public SensorDataApiControler(PantryZoneService pantryZoneService, SensorDataService sensorDataService) {
        this.pantryZoneService = pantryZoneService;
        this.sensorDataService = sensorDataService;
    }
    @GetMapping("/{pantryZoneID}/time_stamp/{timestamp}/temp/{temp}/hum/{hum}/bright/{bright}")
    //@GetMapping("/{pantryZoneID}/temp/{temp}/hum/{hum}/bright/{bright}")
    public String insertData(@PathVariable int pantryZoneID, @PathVariable String timestamp, @PathVariable int temp, @PathVariable int hum, @PathVariable int bright) {
        PantryZone pantryZone = pantryZoneService.get(pantryZoneID);
        if(pantryZone == null) {
             return "Pantry Zone Not Found";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(timestamp, formatter);

        sensorDataService.add(new SensorData(dateTime, SensorType.valueOf("TEMPERATURE"), temp), pantryZone.getId());
        sensorDataService.add(new SensorData(dateTime, SensorType.valueOf("HUMIDITY"), hum), pantryZone.getId());
        sensorDataService.add(new SensorData(dateTime, SensorType.valueOf("BRIGHTNESS"), bright), pantryZone.getId());

        return "success";
    }
}
