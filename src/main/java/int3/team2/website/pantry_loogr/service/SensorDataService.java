package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.domain.SensorData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SensorDataService {
    List<SensorData> getByPantryZone(int pantryZoneID);
    List<SensorData> getByPantryZoneBetween(int pantryZoneID, LocalDateTime from, LocalDateTime to);
    SensorData add(SensorData sensorData, int pantryZoneId);

    Map<String, Integer> getLatest(int pantryZoneId);
}
