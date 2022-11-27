package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.Tag;
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
                .usingGeneratedKeyColumns("ID");
    }

    private Tag mapRow(ResultSet rs, int rowid) throws SQLException {
        return new Tag(rs.getInt("ID"), rs.getString("NAME"));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query("SELECT * FROM TAGS", this::mapRow);
    }

    @Override
    public Tag get(int id) {
        return jdbcTemplate.query("SELECT * FROM TAGS where id = " + id, this::mapRow).get(0);
    }

    @Override
    public List<Tag> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM TAGS WHERE position(LOWER('" + name + "') in LOWER(NAME)) > 0", this::mapRow);
    }
}
