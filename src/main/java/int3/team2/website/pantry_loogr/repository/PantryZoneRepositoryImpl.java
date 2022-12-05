package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class PantryZoneRepositoryImpl implements PantryZoneRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private List<Recipe> recipeList = new ArrayList<>();

    private PantryZone mapRow(ResultSet rs, int rowid) throws SQLException {
        return new PantryZone(rs.getInt("ID"),
                rs.getString("NAME"),
                rs.getInt("MIN_TEMP"),
                rs.getInt("MAX_TEMP"),
                rs.getInt("MIN_HUM"),
                rs.getInt("MAX_HUM"),
                rs.getInt("MIN_BRIGHT"),
                rs.getInt("MAX_BRIGHT")
                );
    }

    public PantryZoneRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("PANTRY_ZONES")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public PantryZone get(int id) {
        PantryZone pantryZone = jdbcTemplate.query("SELECT * FROM PANTRY_ZONES where ID = " + id, this::mapRow).get(0);
        return pantryZone;
    }

    @Override
    public List<PantryZone> getAll() {
        List<PantryZone> pantryZones = jdbcTemplate.query("SELECT * FROM PANTRY_ZONES", this::mapRow);
        return pantryZones;
    }

    @Override
    public List<PantryZone> getAllForUser(int userId) {
        return null;
    }
}
