package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public IngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("INGREDIENTS")
                .usingGeneratedKeyColumns("ID");
    }

    private Ingredient mapRow(ResultSet rs, int rowid) throws SQLException {
        return new Ingredient(rs.getInt("ID"), rs.getString("NAME"));
    }

    @Override
    public List<Ingredient> findAll() {
        return jdbcTemplate.query("SELECT * FROM INGREDIENTS", this::mapRow);
    }

    @Override
    public Ingredient get(int id) {
        return jdbcTemplate.query("SELECT * FROM INGREDIENTS where id = " + id, this::mapRow).get(0);
    }

    @Override
    public List<Ingredient> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM INGREDIENTS WHERE position(LOWER(?) in LOWER(NAME)) > 0", new Object[] {name}, this::mapRow);
    }

    @Override
    public List<Ingredient> findIngredientsByRecipeId(int id) {
        return jdbcTemplate.query("SELECT * FROM RECIPE_INGREDIENTS JOIN INGREDIENTS ON INGREDIENTS.ID = RECIPE_INGREDIENTS.INGREDIENT_ID WHERE RECIPE_INGREDIENTS.RECIPE_ID = " + id, this::mapRow);
    }
}
