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
                .withTableName("END_USERS")
                .usingGeneratedKeyColumns("id");
        this.userPreferenceInsertser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USER_PREFERENCES")
                .usingColumns("user_id", "tag_id", "'LIKE'");
    }

    private EndUser mapRow(ResultSet rs, int rowid) throws SQLException {
        return new EndUser(rs.getInt("ID"),
                rs.getString("PASSWORD"),
                rs.getString("USERNAME"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("CITY"),
                rs.getString("STATE_REGION"),
                rs.getString("ZIP"),
                rs.getString("COUNTRY"),
                rs.getInt("CURRENT_RECIPE"));
    }

    @Override
    public List<EndUser> findAll() {
        return jdbcTemplate.query("SELECT * FROM END_USERS", this::mapRow);
    }

    @Override
    public EndUser get(int id) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE ID = ?", this::mapRow, id).get(0);
    }

    @Override
    public EndUser add(EndUser user) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "INSERT INTO END_USERS(USERNAME, EMAIL, PASSWORD, CURRENT_RECIPE) " +
                        "VALUES (?, ?, ?, ?)",
                VARCHAR, VARCHAR, VARCHAR, INTEGER
        );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        0
                )
        );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return this.get(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public EndUser findByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM END_USERS WHERE USERNAME = ?", this::mapRow, username);
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
