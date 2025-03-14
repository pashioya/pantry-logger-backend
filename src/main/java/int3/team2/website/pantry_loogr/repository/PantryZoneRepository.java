package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.SensorData;

import java.util.List;
import java.util.Map;

public interface PantryZoneRepository {
    PantryZone get(int id);
    PantryZone create(PantryZone pantryzone);
    void update(PantryZone pantryzone);
    PantryZone getBySensorBoxCode(String sensorBoxCode);
    List<PantryZone> getAll();
    List<PantryZone> getAllForUser(int userId);
}
