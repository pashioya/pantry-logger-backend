package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Item;
import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.repository.PantryZoneRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PantryZoneServiceImpl implements PantryZoneService {
    private PantryZoneRepository pantryZoneRepository;
    private ItemService itemService;

    public PantryZoneServiceImpl(PantryZoneRepository pantryZoneRepository, ItemService itemService) {
        this.pantryZoneRepository = pantryZoneRepository;
        this.itemService = itemService;
    }

    @Override
    public List<PantryZone> getAll() {
        List<PantryZone> pantryZones = pantryZoneRepository.getAll();
        for (int i = 0; i < pantryZones.size(); i++) {
            pantryZones.get(i).setItems(itemService.getByPantryZoneId(pantryZones.get(i).getId()));
        }
        return pantryZones;
    }

    public List<HashMap<String, String>> getAllForUser() {
        List<PantryZone> pantryZones = pantryZoneRepository.getAll();
        List<HashMap<String, String>> itemMap = new ArrayList<>();
        for(PantryZone pantryZone: pantryZones) {
            List<Item> items = itemService.getByPantryZoneId(pantryZone.getId());
            for(Item item: items) {
                HashMap map = new HashMap();
                map.put("name", item.getName() + " (" + item.getSize() + ')');
                map.put("quantity", item.getQuantity());
                map.put("location", pantryZone.getName());
                itemMap.add(map);
            }
        }

        return itemMap;
    }

    @Override
    public PantryZone get(int pantryZoneID) {
        PantryZone pantryZone = pantryZoneRepository.get(pantryZoneID);
        pantryZone.setItems(itemService.getByPantryZoneId(pantryZone.getId()));
        return pantryZone;
    }

    @Override
    public List<PantryZone> add(PantryZone pantryZone) {
        return null;
    }
}
