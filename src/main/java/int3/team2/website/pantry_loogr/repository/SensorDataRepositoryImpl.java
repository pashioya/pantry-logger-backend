package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.PantryZone;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.SensorData;
import int3.team2.website.pantry_loogr.domain.SensorType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private List<Recipe> recipeList = new ArrayList<>();

    public SensorDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("SENSOR_DATA")
                .usingGeneratedKeyColumns("ID");
    }
    private SensorData mapRow(ResultSet rs, int rowid) throws SQLException {
        return new SensorData(
                rs.getInt("ID"),
                rs.getTimestamp("TIME_STAMP").toLocalDateTime(),
                SensorType.valueOf(rs.getString("SENSOR_TYPE")),
                rs.getInt("SENSOR_VALUE"),
                rs.getInt("PANTRY_ZONE_ID")
        );
    }
    @Override
    public List<SensorData> getByPantryZone(int pantryZoneId) {
        return jdbcTemplate.query("SELECT * FROM SENSOR_DATA where PANTRY_ZONE_ID = ?", new Object[] { pantryZoneId }, this::mapRow);
    }

    @Override
    public List<SensorData> getByPantryZoneBetween(int pantryZoneId, LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query("SELECT * FROM SENSOR_DATA where PANTRY_ZONE_ID = ? AND TIME_STAMP BETWEEN ? AND ?", new Object[] { pantryZoneId, Timestamp.valueOf(from), Timestamp.valueOf(to) }, this::mapRow);
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
}
