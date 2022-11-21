package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Difficulty;
import int3.team2.website.pantry_loogr.domain.EndUser;
import int3.team2.website.pantry_loogr.domain.Recipe;
import int3.team2.website.pantry_loogr.domain.Time;
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
        return null;
    }

    @Override
    public EndUser get(int id) {
        return null;
    }

    @Override
    public List<EndUser> findByUsername(String username) {
        return null;
    }

    @Override
    public List<EndUser> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public List<EndUser> findByLastName(String lastName) {
        return null;
    }

    @Override
    public List<EndUser> findByEmail(String email) {
        return null;
    }

    @Override
    public List<EndUser> findByCity(String city) {
        return null;
    }

    @Override
    public List<EndUser> findByStateRegion(String stateRegion) {
        return null;
    }

    @Override
    public List<EndUser> findByZip(String zip) {
        return null;
    }

    @Override
    public List<EndUser> findByCountry(String country) {
        return null;
    }
}
