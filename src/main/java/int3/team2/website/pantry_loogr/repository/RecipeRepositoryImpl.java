package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private List<Recipe> recipeList = new ArrayList<>();

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
                Time.valueOf(rs.getString("TIME")));

    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = jdbcTemplate.query("SELECT * FROM RECIPES", this::mapRow);
        return recipes;
    }

    @Override
    public Recipe get(int id) {
        Recipe recipe = jdbcTemplate.query("SELECT * FROM RECIPE where ID = " + id, this::mapRow).get(0);
        return recipe;
    }

    @Override
    public List<Recipe> findByName(String name) {
        return null;
    }

    @Override
    public List<Recipe> findByDifficulty(Difficulty difficulty) {
        return null;
    }

    @Override
    public List<Recipe> findByTime(Time time) {
        return null;
    }
}
