package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.EndUser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    private EndUser mapUserRow(ResultSet rs, int rowid) throws SQLException {
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
    public EndUser get(int id) {
        List<EndUser> list = jdbcTemplate.query("SELECT * FROM end_users WHERE ID = ?", this::mapUserRow, id);
        return list.isEmpty() ? null : list.get(0);
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
        List<EndUser> list = jdbcTemplate.query("SELECT * FROM end_users WHERE username = ?", this::mapUserRow, username);
        return list.isEmpty() ? null : list.get(0);
    }

    public EndUser findByEmail(String email) {
        List<EndUser> list = jdbcTemplate.query("SELECT * FROM END_USERS WHERE EMAIL = ?", this::mapUserRow, email);
        return list.isEmpty() ? null : list.get(0);
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

    @Override
    public List<EndUser> findAll() {
        return jdbcTemplate.query("SELECT * FROM end_users", this::mapUserRow);
    }

}
