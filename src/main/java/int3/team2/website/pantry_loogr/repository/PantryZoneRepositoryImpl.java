package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class PantryZoneRepositoryImpl implements PantryZoneRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private List<Recipe> recipeList = new ArrayList<>();

    public PantryZoneRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("pantry_zones")
                .usingGeneratedKeyColumns("id");
    }

    private PantryZone mapRow(ResultSet rs, int rowid) throws SQLException {
        return new PantryZone(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("min_temp"),
                rs.getInt("max_temp"),
                rs.getInt("min_hum"),
                rs.getInt("max_hum"),
                rs.getInt("min_bright"),
                rs.getInt("max_bright")
        );
    }

    @Override
    public PantryZone get(int id) {
        return jdbcTemplate.query("SELECT * FROM pantry_zones where id = ?", this::mapRow, id).get(0);
    }

    @Override
    public PantryZone getBySensorBoxCode(String sensorBoxCode) {
        return jdbcTemplate.query("SELECT * FROM pantry_zones where sensor_box_code = ?", this::mapRow, sensorBoxCode).get(0);
    }

    @Override
    public List<PantryZone> getAll() {
        return jdbcTemplate.query("SELECT * FROM pantry_zones", this::mapRow);
    }

    @Override
    public List<PantryZone> getAllForUser(int userId) {
        String sql = "SELECT * FROM pantry_zones WHERE user_id = ?";
        return jdbcTemplate.query(sql, preparedStatement -> preparedStatement.setInt(1, userId), this::mapRow);
    }

    @Override
    public PantryZone create(PantryZone pantryzone) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", pantryzone.getUserId());
        parameters.put("name", pantryzone.getName());
        parameters.put("sensor_box_code", pantryzone.getSensorBoxCode());
        parameters.put("min_temp", pantryzone.getMinTemp());
        parameters.put("max_temp", pantryzone.getMaxTemp());
        parameters.put("min_hum", pantryzone.getMinHum());
        parameters.put("max_hum", pantryzone.getMaxHum());
        parameters.put("min_bright", pantryzone.getMinBright());
        parameters.put("max_bright", pantryzone.getMaxBright());

        pantryzone.setId(inserter.executeAndReturnKey(parameters).intValue());
        return pantryzone;
    }

    @Override
    public void update(PantryZone pantryzone) {
        jdbcTemplate.update(
                "UPDATE pantry_zones SET " +
                        "name = ?, " +
                        "sensor_box_code = ?, " +
                        "min_temp = ?," +
                        "max_temp = ?, " +
                        "min_hum = ?," +
                        "max_hum = ?, " +
                        "min_bright = ?," +
                        "max_bright = ? " +
                    "WHERE pantry_zones.id = ?;",
                pantryzone.getName(), pantryzone.getSensorBoxCode(), pantryzone.getMinTemp(), pantryzone.getMaxTemp(), pantryzone.getMinHum(), pantryzone.getMaxHum(), pantryzone.getMinBright(), pantryzone.getMaxBright(), pantryzone.getId()
        );
    }
}
