package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
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
    private IngredientService ingredientService;

    public PantryZoneServiceImpl(PantryZoneRepository pantryZoneRepository, IngredientService ingredientService) {
        this.pantryZoneRepository = pantryZoneRepository;
        this.ingredientService = ingredientService;
    }

    @Override
    public List<PantryZone> getAll() {
        List<PantryZone> pantryZones = pantryZoneRepository.getAll();
        for (int i = 0; i < pantryZones.size(); i++) {
            pantryZones.get(i).setProducts(ingredientService.getByPantryZoneId(pantryZones.get(i).getId()));
        }
        return pantryZones;
    }

    public List<HashMap<String, String>> getAllForUser() {
        List<PantryZone> pantryZones = pantryZoneRepository.getAll();
        List<HashMap<String, String>> itemMap = new ArrayList<>();
        for(PantryZone pantryZone: pantryZones) {
            List<PantryZoneProduct> products = ingredientService.getByPantryZoneId(pantryZone.getId());
            for(PantryZoneProduct product: products) {
                HashMap map = new HashMap();
                map.put("productName", product.getProductName() + " (" + product.getSize() + ')');
                map.put("name", product.getName());
                map.put("quantity", product.getQuantity());
                map.put("location", pantryZone.getName());
                itemMap.add(map);
            }
        }

        return itemMap;
    }

    @Override
    public Map<PantryZoneProduct, PantryZone> getAllForUserByPantry() {
        return null;
    }

    @Override
    public PantryZone get(int pantryZoneID) {
        PantryZone pantryZone = pantryZoneRepository.get(pantryZoneID);
        pantryZone.setProducts(ingredientService.getByPantryZoneId(pantryZone.getId()));
        return pantryZone;
    }

    @Override
    public List<PantryZone> add(PantryZone pantryZone) {
        return null;
    }
}
