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
                .withTableName("PANTRY_ZONES")
                .usingGeneratedKeyColumns("ID");
    }

    private PantryZone mapRow(ResultSet rs, int rowid) throws SQLException {
        return new PantryZone(
                rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getInt("MIN_TEMP"),
                rs.getInt("MAX_TEMP"),
                rs.getInt("MIN_HUM"),
                rs.getInt("MAX_HUM"),
                rs.getInt("MIN_BRIGHT"),
                rs.getInt("MAX_BRIGHT")
        );
    }

    @Override
    public PantryZone get(int id) {
        return jdbcTemplate.query("SELECT * FROM PANTRY_ZONES where ID = ?", this::mapRow, id).get(0);
    }

    @Override
    public PantryZone getBySensorBoxCode(String sensorBoxCode) {
        return jdbcTemplate.query("SELECT * FROM PANTRY_ZONES where SENSOR_BOX_CODE = ?", this::mapRow, sensorBoxCode).get(0);
    }

    @Override
    public List<PantryZone> getAll() {
        return jdbcTemplate.query("SELECT * FROM PANTRY_ZONES", this::mapRow);
    }

    @Override
    public List<PantryZone> getAllForUser(int userId) {
        String sql = "SELECT * FROM PANTRY_ZONES WHERE USER_ID = ?";
        return jdbcTemplate.query(sql, preparedStatement -> preparedStatement.setInt(1, userId), this::mapRow);
    }

    @Override
    public PantryZone create(PantryZone pantryzone) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", pantryzone.getUserId());
        parameters.put("NAME", pantryzone.getName());
        parameters.put("MIN_TEMP", pantryzone.getMinTemp());
        parameters.put("MAX_TEMP", pantryzone.getMaxTemp());
        parameters.put("MIN_HUM", pantryzone.getMinHum());
        parameters.put("MAX_HUM", pantryzone.getMaxHum());
        parameters.put("MIN_BRIGHT", pantryzone.getMinBright());
        parameters.put("MAX_BRIGHT", pantryzone.getMaxBright());

        pantryzone.setId(inserter.executeAndReturnKey(parameters).intValue());
        return pantryzone;
    }

    @Override
    public void update(PantryZone pantryzone) {
        jdbcTemplate.update(
                "UPDATE PANTRY_ZONES SET " +
                        "NAME = ?, " +
                        "SENSOR_BOX_CODE = ?, " +
                        "MIN_TEMP = ?," +
                        "MAX_TEMP = ?, " +
                        "MIN_HUM = ?," +
                        "MAX_HUM = ?, " +
                        "MIN_BRIGHT = ?," +
                        "MAX_BRIGHT = ? " +
                    "WHERE PANTRY_ZONES.ID = ?;",
                pantryzone.getName(), pantryzone.getSensorBoxCode(), pantryzone.getMinTemp(), pantryzone.getMaxTemp(), pantryzone.getMinHum(), pantryzone.getMaxHum(), pantryzone.getMinBright(), pantryzone.getMaxBright(), pantryzone.getId()
        );
    }
}
