package int3.team2.website.pantry_loogr.repository;

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
    private SimpleJdbcInsert tagInserter;
    private SimpleJdbcInsert preferenceInserter;
    private SimpleJdbcInsert recipeTagInserter;
    private SimpleJdbcInsert userPreferenceInserter;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TAGS")
                .usingGeneratedKeyColumns("TAG_ID");
        this.preferenceInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("USER_PREFERENCES").usingColumns("USER_ID", "TAG_ID", "LIKES");
        this.recipeTagInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("RECIPE_TAGS").usingColumns("TAG_ID", "RECIPE_ID");
    }

    private Tag mapRow(ResultSet rs, int rowid) throws SQLException {
        return new Tag(
                rs.getInt("TAG_ID"),
                rs.getString("NAME"),
                TagFlag.valueOf(rs.getString("FLAG"))
        );
    }

    private Map<Tag, Boolean> mapTagWithBoolean(ResultSet rs, int rowid) throws SQLException {
        Map<Tag, Boolean> map = new HashMap<>();
        map.put(
                new Tag(
                        rs.getInt("TAG_ID"),
                        rs.getString("NAME"),
                        TagFlag.valueOf(rs.getString("FLAG"))
                ),
                rs.getBoolean("LIKES")
        );
        return map;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("SELECT * FROM TAGS", this::mapRow);
    }

    @Override
    public Tag get(int id) {
        return jdbcTemplate.query("SELECT * FROM TAGS WHERE TAG_ID = ?", this::mapRow, id).get(0);
    }

    @Override
    public Tag createTag(Tag tag) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("FLAG", tag.getFlag().toString());
        parameters.put("NAME", tag.getName());
        tag.setId(tagInserter.executeAndReturnKey(parameters).intValue());
        return tag;
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
    public Map<Tag, Boolean> getAllByUser(int userId) {
        List<Map<Tag, Boolean>> list = jdbcTemplate.query("SELECT TAGS.*, LIKES FROM USER_PREFERENCES JOIN TAGS USING(TAG_ID) WHERE USER_PREFERENCES.USER_ID = ?", this::mapTagWithBoolean, userId);
        Map<Tag, Boolean> map = new HashMap<>();
        list.forEach(x -> {
            x.keySet().forEach( y -> {
                map.put(y, x.get(y));
            });
        });
        return map;
    }

    @Override
    public void addUserPreference(int userId, int tagId, boolean like) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", userId);
        parameters.put("TAG_ID", tagId);
        parameters.put("LIKES", like);
        preferenceInserter.execute(parameters);
    }

    @Override
    public void removeUserPreference(int userId, int tagId) {
        jdbcTemplate.update(
                "DELETE FROM USER_PREFERENCES WHERE USER_ID = ? AND TAG_ID = ?;", userId, tagId
        );
    }

    @Override
    public List<Tag> getLikesByUserId(int userId) {
        return jdbcTemplate.query("SELECT TAGS.* FROM USER_PREFERENCES JOIN TAGS USING(TAG_ID) WHERE USER_PREFERENCES.USER_ID = ? AND USER_PREFERENCES.LIKES = TRUE", this::mapRow, userId);
    }

    @Override
    public List<Tag> getDislikesByUserId(int userId) {
        return jdbcTemplate.query("SELECT TAGS.* FROM USER_PREFERENCES JOIN TAGS USING(TAG_ID) WHERE USER_PREFERENCES.USER_ID = ? AND USER_PREFERENCES.LIKES = FALSE", this::mapRow, userId);
    }

    @Override
    public List<Tag> addToRecipeRelationTable(int recipeId, List<Tag> tagList) {
        for (Tag tag : tagList) {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("RECIPE_ID", recipeId);
            parameters.put("TAG_ID", tag.getId());
            recipeTagInserter.execute(parameters);
        }
        return tagList;
    }

}
