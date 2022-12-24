package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.SensorData;
import int3.team2.website.pantry_loogr.domain.Time;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SensorDataRepository {
    SensorData create(SensorData sensorData, int pantryZoneId);
    List<SensorData> getByPantryZone(int pantryZoneId);
    List<SensorData> getByPantryZoneBetween(int pantryZoneId, LocalDateTime from, LocalDateTime to);
    int getLatestTemp(int pantryZoneId);
    int getLatestHum(int pantryZoneId);
    int getLatestBright(int pantryZoneId);
}
