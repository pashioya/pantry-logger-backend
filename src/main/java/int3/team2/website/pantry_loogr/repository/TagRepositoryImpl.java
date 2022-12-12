package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.domain.TagFlag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private SimpleJdbcInsert recipeTagInserter;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TAGS")
                .usingGeneratedKeyColumns("TAG_ID");
        this.recipeTagInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("RECIPE_TAGS").usingColumns("TAG_ID", "RECIPE_ID");
    }

    private Tag mapRow(ResultSet rs, int rowid) throws SQLException {
        return new Tag(rs.getInt("TAG_ID"), rs.getString("NAME"), TagFlag.valueOf(rs.getString("FLAG")));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("SELECT * FROM TAGS", this::mapRow);
    }

    @Override
    public Tag get(int id) {
        return jdbcTemplate.query("SELECT * FROM TAGS where id = ?", this::mapRow, id).get(0);
    }

    @Override
    public List<Tag> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM TAGS WHERE NAME LIKE ?", this::mapRow, name);
    }

    @Override
    public List<Tag> getByRecipeId(int recipeId) {
        //return jdbcTemplate.query("SELECT TAGS.* FROM RECIPE_TAGS JOIN TAGS USING(TAG_ID) WHERE RECIPE_ID = ?", this::mapRow, recipeId);
        String sql = "SELECT " +
                "       TAGS.*" +
                "     FROM " +
                "       INGREDIENT_TAGS  " +
                "     LEFT JOIN TAGS ON INGREDIENT_TAGS.TAG_ID=TAGS.TAG_ID " +
                "     WHERE INGREDIENT_TAGS.INGREDIENT_ID IN" +
                " (SELECT INGREDIENTS.ID" +
                "   FROM RECIPES" +
                "   JOIN RECIPE_INGREDIENTS ON RECIPES.ID = RECIPE_INGREDIENTS.RECIPE_ID" +
                "   JOIN INGREDIENTS ON INGREDIENTS .ID = RECIPE_INGREDIENTS.INGREDIENT_ID " +
                "   WHERE RECIPE_ID = ? " +
                "   ) " +
                "UNION " +
                "SELECT " +
                "   TAGS.*" +
                "FROM TAGS " +
                "LEFT JOIN RECIPE_TAGS ON RECIPE_TAGS.TAG_ID=TAGS.TAG_ID " +
                "WHERE " +
                "   RECIPE_TAGS.RECIPE_ID = ?;";
        return jdbcTemplate.query(sql, this::mapRow, recipeId, recipeId);
    }
    @Override
    public List<Tag> getByIngredientId(int ingredientId) {
        return jdbcTemplate.query("SELECT TAGS.* FROM INGREDIENT_TAGS JOIN TAGS USING(TAG_ID) WHERE INGREDIENTS.ID = ?", this::mapRow, ingredientId);
    }
    @Override
    public List<Tag> getLikesByUserId(int userId) {
        return jdbcTemplate.query("SELECT TAGS.* FROM USER_PREFERENCES JOIN TAGS USING(TAG_ID) WHERE USER_PREFERENCES.USER_ID = ? AND USER_PREFERENCES.\"LIKE\" = TRUE", this::mapRow, userId);
    }
    @Override
    public List<Tag> getDislikesByUserId(int userId) {
        return jdbcTemplate.query("SELECT TAGS.* FROM USER_PREFERENCES JOIN TAGS USING(TAG_ID) WHERE USER_PREFERENCES.USER_ID = ? AND USER_PREFERENCES.\"LIKE\" = FALSE", this::mapRow, userId);
    }

    @Override
    public List<Tag> addToRelationTable(int recipeId, List<Tag> tagList) {
        /*for (Tag tag: tagList) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("RECIPE_ID", recipeId);
            parameters.put("TAG_ID", tag.getId());
            recipeTagInserter.execute(parameters);
        }*/
        return tagList;
    }
}
