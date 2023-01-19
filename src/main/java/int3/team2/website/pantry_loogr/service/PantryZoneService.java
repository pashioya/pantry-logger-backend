package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PantryZoneService {
    List<PantryZone> getAll();
    List<PantryZone> getAllForUser(int userID);
    PantryZone get(int pantryZoneID);
    PantryZone add(PantryZone pantryZone);
    void update(PantryZone pantryzone);
    PantryZone getBySensorBoxCode(String sensorBoxCode);
}
