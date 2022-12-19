package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.PantryZoneProduct;
import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.repository.PantryZoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.expression.Lists;

import java.util.*;

@Component
public class PantryZoneServiceImpl implements PantryZoneService {
    private final Logger logger;
    private PantryZoneRepository pantryZoneRepository;
    private IngredientService ingredientService;
    private SensorDataService sensorDataService;

    public PantryZoneServiceImpl(PantryZoneRepository pantryZoneRepository, IngredientService ingredientService, SensorDataService sensorDataService) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.pantryZoneRepository = pantryZoneRepository;
        this.ingredientService = ingredientService;
        this.sensorDataService = sensorDataService;
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
    public List<PantryZone> getAllForUser(int userID) {
        List<PantryZone> pantryZones = pantryZoneRepository.getAllForUser(userID);
        pantryZones.forEach(pantryZone -> {
            pantryZone.setProducts(this.ingredientService.getByPantryZoneId(pantryZone.getId()));
            pantryZone.setEnviro(this.sensorDataService.getLatest(pantryZone.getId()));
        });

        return pantryZones;
    }

    @Override
    public PantryZone get(int pantryZoneID) {
        PantryZone pantryZone = pantryZoneRepository.get(pantryZoneID);
        pantryZone.setProducts(ingredientService.getByPantryZoneId(pantryZone.getId()));
        return pantryZone;
    }

    @Override
    public PantryZone add(PantryZone pantryZone) {
        return pantryZoneRepository.create(pantryZone);
    }

    @Override
    public void update(PantryZone pantryzone) {
        pantryZoneRepository.update(pantryzone);
    }
}
