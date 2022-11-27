package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.repository.PantryZoneRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PantryZoneServiceImpl implements PantryZoneService {
    private PantryZoneRepository pantryZoneRepository;

    public PantryZoneServiceImpl(PantryZoneRepository pantryZoneRepository) {
        this.pantryZoneRepository = pantryZoneRepository;
    }

    @Override
    public List<PantryZone> getAll() {
        return pantryZoneRepository.getAll();
    }

    @Override
    public PantryZone get(int pantryZoneID) {
        return pantryZoneRepository.get(pantryZoneID);
    }

    @Override
    public List<PantryZone> add(PantryZone pantryZone) {
        return null;
    }
}
