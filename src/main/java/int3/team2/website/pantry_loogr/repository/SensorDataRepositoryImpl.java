package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.SensorData;
import int3.team2.website.pantry_loogr.domain.SensorType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SensorDataRepositoryImpl implements SensorDataRepository{
    private Logger logger;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private List<Recipe> recipeList = new ArrayList<>();

    public SensorDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("SENSOR_DATA")
                .usingGeneratedKeyColumns("id");
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
    private SensorData mapSensorDataRow(ResultSet rs, int rowid) throws SQLException {
        return new SensorData(
                rs.getInt("ID"),
                rs.getTimestamp("TIME_STAMP").toLocalDateTime(),
                SensorType.valueOf(rs.getString("SENSOR_TYPE")),
                rs.getInt("SENSOR_VALUE"),
                rs.getInt("PANTRY_ZONE_ID")
        );
    }

    @Override
    public SensorData create(SensorData sensorData, int pantryZoneId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("PANTRY_ZONE_ID", pantryZoneId);
        parameters.put("TIME_STAMP", Timestamp.valueOf(sensorData.getTimestamp()));
        parameters.put("SENSOR_VALUE", sensorData.getValue());
        parameters.put("SENSOR_TYPE", sensorData.getType().toString());
        sensorData.setId(inserter.executeAndReturnKey(parameters).intValue());
        return sensorData;
    }

    @Override
    public List<SensorData> getByPantryZone(int pantryZoneId) {
        return jdbcTemplate.query("SELECT * FROM SENSOR_DATA where PANTRY_ZONE_ID = ?", this::mapSensorDataRow, pantryZoneId);
    }

    @Override
    public List<SensorData> getByPantryZoneBetween(int pantryZoneId, LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query("SELECT * FROM SENSOR_DATA where PANTRY_ZONE_ID = ? AND TIME_STAMP BETWEEN ? AND ?", this::mapSensorDataRow, pantryZoneId, Timestamp.valueOf(from), Timestamp.valueOf(to));
    }

    @Override
    public int getLatestTemp(int pantryZoneId) {
        try {
            SensorData sensorData = jdbcTemplate.queryForObject("SELECT * FROM SENSOR_DATA where PANTRY_ZONE_ID = ? AND SENSOR_TYPE = 'TEMPERATURE' ORDER BY TIME_STAMP DESC LIMIT 1", this::mapSensorDataRow, pantryZoneId);
            return sensorData.getValue();
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public int getLatestHum(int pantryZoneId) {
        try {
            SensorData sensorData = jdbcTemplate.queryForObject("SELECT * FROM SENSOR_DATA where PANTRY_ZONE_ID = ? AND SENSOR_TYPE = 'HUMIDITY' ORDER BY TIME_STAMP DESC LIMIT 1", this::mapSensorDataRow, pantryZoneId);
            return sensorData.getValue();
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public int getLatestBright(int pantryZoneId) {
        try {
            SensorData sensorData = jdbcTemplate.queryForObject("SELECT * FROM SENSOR_DATA where PANTRY_ZONE_ID = ? AND SENSOR_TYPE = 'BRIGHTNESS' ORDER BY TIME_STAMP DESC LIMIT 1", this::mapSensorDataRow, pantryZoneId);
            return sensorData.getValue();
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
}