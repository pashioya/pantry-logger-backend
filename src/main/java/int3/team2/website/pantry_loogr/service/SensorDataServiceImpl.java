package int3.team2.website.pantry_loogr.service;

import int3.team2.website.pantry_loogr.domain.SensorData;
import int3.team2.website.pantry_loogr.repository.PantryZoneRepository;
import int3.team2.website.pantry_loogr.repository.SensorDataRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SensorDataServiceImpl implements SensorDataService{
    private SensorDataRepository sensorDataRepository;

    public SensorDataServiceImpl(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }
    @Override
    public List<SensorData> getByPantryZone(int pantryZoneID) {
        return sensorDataRepository.getByPantryZone(pantryZoneID);
    }

    @Override
    public List<SensorData> getByPantryZoneBetween(int pantryZoneID, LocalDateTime from, LocalDateTime to) {
        return sensorDataRepository.getByPantryZoneBetween(pantryZoneID, from, to);
    }

    @Override
    public SensorData add(SensorData sensorData, int pantryZoneId) {
        return sensorDataRepository.create(sensorData, pantryZoneId);
    }
}
