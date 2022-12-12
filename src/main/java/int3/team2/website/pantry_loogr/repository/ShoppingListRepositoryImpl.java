package int3.team2.website.pantry_loogr.repository;

import int3.team2.website.pantry_loogr.domain.Ingredient;
import int3.team2.website.pantry_loogr.domain.ShoppingList;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

@Repository
public class ShoppingListRepositoryImpl implements ShoppingListRepository{
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert inserter;
    private SimpleJdbcInsert ShoppingListIngredientsInserter;

    public ShoppingListRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.inserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("SHOPPING_LISTS")
                .usingGeneratedKeyColumns("ID");
        this.ShoppingListIngredientsInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("SHOPPING_LISTS_INGREDIENTS");

    }

    private ShoppingList mapRow(ResultSet rs, int rowid) throws SQLException {
        return new ShoppingList(rs.getInt("ID"));
    }

    @Override
    public ShoppingList get(int id) {
        String sql = "SELECT " +
                "          * " +
                "       FROM " +
                "           SHOPPING_LISTS" +
                "       WHERE " +
                "           SHOPPING_LISTS.ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[] {id}, new int[] {INTEGER},  this::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public ShoppingList getByUser(int userId) {
        String sql = "SELECT " +
                "          * " +
                "       FROM " +
                "           SHOPPING_LISTS" +
                "       WHERE " +
                "           SHOPPING_LISTS.USER_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[] {userId}, new int[] {INTEGER},  this::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public ShoppingList removeIngredientByAmount(int shoppingListId, int ingredientId, int amount) {
        String sql = "SELECT " +
                "          * " +
                "       FROM " +
                "           SHOPPING_LISTS" +
                "       " +
                "WHERE " +
                "           SHOPPING_LISTS.SHOPPING_LIST_ID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[] {shoppingListId}, new int[] {INTEGER},  this::mapRow);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public void addIngredientByAmount(int shoppingListId, int ingredientId, int amount) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SHOPPING_LIST_ID", shoppingListId);
        parameters.put("INGREDIENT_ID", ingredientId);
        ShoppingListIngredientsInserter.executeAndReturnKey(parameters).intValue();
    }
}
