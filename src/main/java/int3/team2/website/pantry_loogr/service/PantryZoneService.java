package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PantryZoneService {
    List<PantryZone> getAll();
    PantryZone get(int pantryZoneID);
    List<PantryZone> add(PantryZone pantryZone);
    List<HashMap<String, String>> getAllForUser();
}
