package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Difficulty;
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
        return new Recipe(rs.getInt("RECIPE_ID"),
                rs.getString("RECIPE_NAME"),
                Difficulty.valueOf(rs.getString("RECIPE_DIFFICULTY")),
                rs.getString("RECIPE_DESCRIPTION"),
                rs.getString("RECIPE_INSTRUCTIONS"),
                Time.valueOf(rs.getString("RECIPE_TIME")));
    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = jdbcTemplate.query("SELECT * FROM RECIPES", this::mapRow);
        return recipes;
    }

    @Override
    public List<Recipe> findByName(String recipe_name) {
        return recipeList.stream()
                .filter(recipe -> recipe.getRecipe_name().equals(recipe_name))
                .toList();
    }

    @Override
    public List<Recipe> findByDifficulty(Difficulty recipe_difficulty) {
        return recipeList.stream()
                .filter(recipe -> recipe.getRecipe_difficulty() == recipe_difficulty)
                .toList();
    }

    @Override
    public List<Recipe> findByTime(Time recipe_time) {
        return recipeList.stream()
                .filter(recipe -> recipe.getRecipe_time() == recipe_time)
                .toList();
    }

    @Override
    public List<Recipe> findById(long id) {
        return recipeList.stream()
                .filter(recipe -> recipe.getRecipe_id() == id)
                .toList();
    }
}
