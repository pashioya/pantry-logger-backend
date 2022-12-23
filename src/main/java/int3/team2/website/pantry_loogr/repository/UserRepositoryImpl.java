package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.EndUser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private SimpleJdbcInsert userPreferenceInsertser;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("end_users")
                .usingGeneratedKeyColumns("id");
        this.userPreferenceInsertser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_preferences")
                .usingColumns("user_id", "tag_id", "'LIKE'");
    }

    private EndUser mapRow(ResultSet rs, int rowid) throws SQLException {
        return new EndUser(rs.getInt("id"),
                rs.getString("password"),
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("city"),
                rs.getString("state_region"),
                rs.getString("zip"),
                rs.getString("country"),
                rs.getInt("current_recipe"));
    }

    @Override
    public List<EndUser> findAll() {
        return jdbcTemplate.query("SELECT * FROM end_users", this::mapRow);
    }

    @Override
    public EndUser get(int id) {
        return jdbcTemplate.query("SELECT * FROM end_users WHERE ID = ?", this::mapRow, id).get(0);
    }

    @Override
    public EndUser add(EndUser user) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", user.getUsername());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        parameters.put("current_recipe", 0);

        user.setId(inserter.executeAndReturnKey(parameters).intValue());
        return user;
    }

    @Override
    public EndUser findByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM end_users WHERE username = ?", this::mapRow, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public EndUser findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM END_USERS WHERE EMAIL = ?", this::mapRow, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateUser(EndUser endUser) {
        jdbcTemplate.update(
                "UPDATE END_USERS SET PASSWORD=?, USERNAME=?,FIRST_NAME=?, LAST_NAME=?, EMAIL=?, CITY=?, STATE_REGION=?, ZIP=?, COUNTRY=?, CURRENT_RECIPE=? WHERE ID=?",
                endUser.getPassword(),
                endUser.getUsername(),
                endUser.getFirstName(),
                endUser.getLastName(),
                endUser.getEmail(),
                endUser.getCity(),
                endUser.getStateRegion(),
                endUser.getZip(),
                endUser.getCountry(),
                endUser.getCurrentRecipe(),
                endUser.getId()
        );
    }
}
