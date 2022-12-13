package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public RecipeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RECIPES")
                .usingGeneratedKeyColumns("ID");
    }

    private Recipe mapRow(ResultSet rs, int rowid) throws SQLException {
        return new Recipe(rs.getInt("ID"),
                rs.getString("NAME"),
                Difficulty.valueOf(rs.getString("DIFFICULTY")),
                rs.getString("DESCRIPTION"),
                rs.getString("INSTRUCTIONS"),
                Time.valueOf(rs.getString("TIME")),
                rs.getString("IMAGE_PATH")
        );
    }

    @Override
    public List<Recipe> findAll() {
        return jdbcTemplate.query("SELECT * FROM RECIPES", this::mapRow);
    }

    @Override
    public Recipe get(int id) {
        return jdbcTemplate.query("SELECT * FROM RECIPES WHERE ID = " + id, this::mapRow).get(0);
    }

    @Override
    public List<Recipe> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM RECIPES WHERE position(LOWER('" + name + "') in LOWER(NAME)) > 0", this::mapRow);
    }

    @Override
    public List<Recipe> findByDifficulty(Difficulty difficulty) {
        return jdbcTemplate.query("SELECT * FROM RECIPES WHERE DIFFICULTY = " + difficulty, this::mapRow);
    }

    @Override
    public List<Recipe> findByTime(Time time) {
        return jdbcTemplate.query("SELECT * FROM RECIPES WHERE TIME = " + time, this::mapRow);
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("NAME", recipe.getName());
        parameters.put("DIFFICULTY", recipe.getDifficulty().toString());
        parameters.put("DESCRIPTION", recipe.getDescription());
        parameters.put("INSTRUCTIONS", recipe.getInstructions());
        parameters.put("TIME", recipe.getTime().toString());
        recipe.setId(inserter.executeAndReturnKey(parameters).intValue());
        return recipe;
    }
}
