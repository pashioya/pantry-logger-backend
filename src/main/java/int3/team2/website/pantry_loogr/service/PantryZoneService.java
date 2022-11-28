package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.PantryZone;

import java.util.HashMap;
import java.util.List;

public interface PantryZoneService {
    List<PantryZone> getAll();
    PantryZone get(int pantryZoneID);
    List<PantryZone> add(PantryZone pantryZone);
    public List<HashMap<String, String>> getAllForUser();
}
