package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.EndUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("END_USERS")
                .usingGeneratedKeyColumns("ID");
    }

    private EndUser mapRow(ResultSet rs, int rowid) throws SQLException {
        return new EndUser(rs.getInt("ID"),
                rs.getString("USERNAME"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("CITY"),
                rs.getString("STATE_REGION"),
                rs.getString("ZIP"),
                rs.getString("COUNTRY"));
    }

    @Override
    public List<EndUser> findAll() {
        return jdbcTemplate.query("SELECT * FROM END_USERS", this::mapRow);
    }

    @Override
    public EndUser get(int id) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE ID = " + id, this::mapRow).get(0);
    }

    @Override
    public List<EndUser> findByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE position(LOWER('" + username + "') in LOWER(USERNAME)) > 0", this::mapRow);
    }

    @Override
    public List<EndUser> findByFirstName(String firstName) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE position(LOWER('" + firstName + "') in LOWER(FIRST_NAME)) > 0", this::mapRow);
    }

    @Override
    public List<EndUser> findByLastName(String lastName) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE position(LOWER('" + lastName + "') in LOWER(LAST_NAME)) > 0", this::mapRow);
    }

    @Override
    public List<EndUser> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE position(LOWER('" + email + "') in LOWER(EMAIL)) > 0", this::mapRow);
    }

    @Override
    public List<EndUser> findByCity(String city) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE position(LOWER('" + city + "') in LOWER(CITY)) > 0", this::mapRow);
    }

    @Override
    public List<EndUser> findByStateRegion(String stateRegion) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE position(LOWER('" + stateRegion + "') in LOWER(STATE_REGION)) > 0", this::mapRow);
    }

    @Override
    public List<EndUser> findByZip(String zip) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE position(LOWER('" + zip + "') in LOWER(ZIP)) > 0", this::mapRow);
    }

    @Override
    public List<EndUser> findByCountry(String country) {
        return jdbcTemplate.query("SELECT * FROM END_USERS WHERE position(LOWER('" + country + "') in LOWER(COUNTRY)) > 0", this::mapRow);
    }
}
