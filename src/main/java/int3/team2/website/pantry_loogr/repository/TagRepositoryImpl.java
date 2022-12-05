package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Tag;
import int3.team2.website.pantry_loogr.domain.TagFlag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TAGS")
                .usingGeneratedKeyColumns("TAG_ID");
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
        return jdbcTemplate.query("SELECT TAGS.* FROM RECIPE_TAGS JOIN TAGS USING(TAG_ID) WHERE RECIPE_ID = ?", this::mapRow, recipeId);
    }
}
