package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.repository.IngredientRepository;
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
        for (PantryZone pantryZone : pantryZones) {
            pantryZone.setProducts(ingredientService.getByPantryZoneId(pantryZone.getId()));
        }
        return pantryZones;
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

    @Override
    public List<HashMap<String, String>> getAllForUser() {
        return null;
    }
}
