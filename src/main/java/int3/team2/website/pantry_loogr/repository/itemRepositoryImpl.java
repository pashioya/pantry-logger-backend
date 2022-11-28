package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class itemRepositoryImpl implements ItemRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private SimpleJdbcInsert pantryZoneInserter;

    public itemRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("INGREDIENT_ITEMS")
                .usingGeneratedKeyColumns("ID");
        this.pantryZoneInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Pantry_ZONE_ITEMS");
    }

    private Item mapRow(ResultSet rs, int rowid) throws SQLException {
        return new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getString("CODE"), rs.getInt("SIZE"), rs.getInt("QUANTITY"), rs.getInt("INGREDIENT_ID"));
    }
    private Item mapRowItemOnly(ResultSet rs, int rowid) throws SQLException {
        return new Item(rs.getInt("ID"), rs.getString("NAME"), rs.getString("CODE"), rs.getInt("SIZE"), rs.getInt("INGREDIENT_ID"));
    }

    @Override
    public List<Item> getAll() {
        return jdbcTemplate.query("SELECT * FROM INGREDIENT_ITEMS", this::mapRow);
    }

    @Override
    public Item get(int id) {
        return null;
    }

    @Override
    public Item getbyCode(String code) {
        return jdbcTemplate.query("SELECT * FROM INGREDIENT_ITEMS WHERE INGREDIENT_ITEMS.CODE = ?",new Object[] {code}, this::mapRowItemOnly).get(0);
    }
    @Override
    public List<Item> getByPantryZoneId(int pantryZoneId) {
        return jdbcTemplate.query("SELECT * FROM PANTRY_ZONE_ITEMS LEFT JOIN INGREDIENT_ITEMS ON INGREDIENT_ITEMS.ID = PANTRY_ZONE_ITEMS.ITEM_ID WHERE PANTRY_ZONE_ITEMS.PANTRY_ZONE_ID = ?",new Object[] {pantryZoneId}, this::mapRow);
    }
    @Override
    public void addToPantry(int itemId, int pantryZoneId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ITEM_ID", itemId);
        parameters.put("PANTRY_ZONE_ID", pantryZoneId);
        parameters.put("QUANTITY", 1);
        pantryZoneInserter.execute(parameters);
    }
}
